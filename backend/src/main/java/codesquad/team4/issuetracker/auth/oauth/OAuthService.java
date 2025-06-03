package codesquad.team4.issuetracker.auth.oauth;

import codesquad.team4.issuetracker.auth.JwtProvider;
import codesquad.team4.issuetracker.auth.LoginType;
import codesquad.team4.issuetracker.auth.dto.AuthResponseDto;
import codesquad.team4.issuetracker.entity.User;
import codesquad.team4.issuetracker.exception.ExceptionMessage;
import codesquad.team4.issuetracker.exception.badrequest.InvalidLoginTypeException;
import codesquad.team4.issuetracker.exception.badrequest.InvalidOAuthStateException;
import codesquad.team4.issuetracker.auth.oauth.dto.OAuthRequestDto;
import codesquad.team4.issuetracker.auth.oauth.dto.OAuthResponseDto;
import codesquad.team4.issuetracker.exception.notfound.EmailNotFoundException;
import codesquad.team4.issuetracker.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OAuthService {
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Value("${github.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    private static final String GITHUB_AUTHORIZE_URL = "https://github.com/login/oauth/authorize";
    private static final String GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String GITHUB_USER_API_URL = "https://api.github.com/user";
    private static final String GITHUB_USER_EMAIL_API_URL = "https://api.github.com/user/emails";


    public OAuthResponseDto.OAuthLoginUrl buildGithubAuthorizeUrl(String state) {
        //Github 인증 URL 구성
        String githubUrl = UriComponentsBuilder.fromHttpUrl(GITHUB_AUTHORIZE_URL)
            .queryParam("client_id", clientId)
            .queryParam("redirect_uri", redirectUri)
            .queryParam("scope", "read:user user:email")
            .queryParam("state", state)
            .build()
            .toUriString();

        return OAuthResponseDto.OAuthLoginUrl.builder()
            .url(githubUrl)
            .build();
    }

    public String createGithubAuthorizeState() {
        //state 생성
        return UUID.randomUUID().toString();
    }

    public User handleCallback(OAuthRequestDto.GitHubCallback callback, HttpSession session) {
        //state 값 비교
        String savedState = (String) session.getAttribute("oauth_state");
        if (!callback.getState().equals(savedState)) {
            throw new InvalidOAuthStateException();
        }

        //access tocken 요청
        String accessToken = requestAccessToken(callback.getCode());
        //Github 사용자 정보 요청
        OAuthResponseDto.GitHubUserResponse userInfo = fetchGitHubUser(accessToken);
        //회원가입/로그인 처리
        Optional<User> optionalUser = userRepository.findByEmail(userInfo.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            validateLoginType(user);
            return user;
        } else {
            return registerUser(userInfo);
        }
    }

    private String requestAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        //request
        Map<String, String> body = Map.of(
            "client_id", clientId,
            "client_secret", clientSecret,
            "code", code,
            "redirect_uri", redirectUri
        );

        //header, body를 합쳐서 하나의 요청으로 만듦
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        //Post 요청
        ResponseEntity<Map> response = restTemplate.postForEntity(
            GITHUB_TOKEN_URL,
            request,
            Map.class
        );

        return (String) response.getBody().get("access_token");
    }

    private OAuthResponseDto.GitHubUserResponse fetchGitHubUser(String accessToken) {
        //Authorization 포함된 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> request = new HttpEntity<>(headers);

        //Get 요청
        ResponseEntity<OAuthResponseDto.GitHubUserResponse> response = restTemplate.exchange(
            GITHUB_USER_API_URL,
            HttpMethod.GET,
            request,
            OAuthResponseDto.GitHubUserResponse.class
        );

        OAuthResponseDto.GitHubUserResponse userResponse = response.getBody();
        // 이메일이 null(비공개)이면 /user/emails API 호출
        if (userResponse.getEmail() == null) {
            String fallbackEmail = fetchPrimaryEmail(accessToken);
            userResponse.setEmail(fallbackEmail);
        }

        return userResponse;
    }

    public AuthResponseDto.LoginResponseDto loginUser(User user) {
        return AuthResponseDto.LoginResponseDto.builder()
            .userId(user.getId())
            .nickname(user.getNickname())
            .profileImage(user.getProfileImage())
            .build();
    }

    public User registerUser(OAuthResponseDto.GitHubUserResponse userInfo) {
        return userRepository.save(
            User.builder()
                .email(userInfo.getEmail())
                .nickname(userInfo.getNickname())
                .profileImage(userInfo.getAvatarUrl())
                .loginType(LoginType.GITHUB)
                .build()
        );
    }

    private void validateLoginType(User user) {
        if (user.getLoginType() != LoginType.GITHUB) {
            throw new InvalidLoginTypeException(ExceptionMessage.INVALID_LOGINTYPE_GITHUB);
        }
    }

    //이메일 비공개인 경우 user/emails
    public String fetchPrimaryEmail(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<OAuthResponseDto.GitHubEmailResponse[]> response = restTemplate.exchange(
            GITHUB_USER_EMAIL_API_URL,
            HttpMethod.GET,
            request,
            OAuthResponseDto.GitHubEmailResponse[].class
        );

        return Arrays.stream(response.getBody())
            .filter(OAuthResponseDto.GitHubEmailResponse::isPrimary)
            .map(OAuthResponseDto.GitHubEmailResponse::getEmail)
            .findFirst()
            .orElseThrow(() -> new EmailNotFoundException());
    }
}
