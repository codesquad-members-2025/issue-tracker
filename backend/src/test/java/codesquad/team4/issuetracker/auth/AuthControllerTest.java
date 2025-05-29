package codesquad.team4.issuetracker.auth;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import codesquad.team4.issuetracker.auth.dto.AuthRequestDto;
import codesquad.team4.issuetracker.entity.User;
import codesquad.team4.issuetracker.exception.unauthorized.UserByEmailNotFoundException;
import codesquad.team4.issuetracker.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtProvider jwtProvider;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private AuthInterceptor authInterceptor;

    private User dummyUser;

    @BeforeEach
    void setUp() {
        dummyUser = User.builder()
            .id(1L)
            .email("test@example.com")
            .nickname("테스터")
            .password("encodedPassword")
            .profileImage("image.png")
            .build();
    }

    @Test
    @DisplayName("로그인 성공시 유저 정보와 쿠키에 토큰을 반환한다")
    void loginSuccessTest() throws Exception {
        // given
        AuthRequestDto.LoginRequestDto loginRequest = new AuthRequestDto.LoginRequestDto("test@example.com", "1234");
        String jwtToken = "mocked-jwt-token";

        given(authService.checkEmailAndPassword(any())).willReturn(dummyUser);
        given(jwtProvider.createToken(dummyUser.getId())).willReturn(jwtToken);

        // when & then
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.userId").value(dummyUser.getId()))
            .andExpect(jsonPath("$.data.nickname").value(dummyUser.getNickname()))
            .andExpect(jsonPath("$.data.profileImage").value(dummyUser.getProfileImage()))
            .andExpect(header().string("Set-Cookie", containsString("jwt=")))
            .andExpect(header().string("Set-Cookie", containsString("HttpOnly")))
            .andExpect(header().string("Set-Cookie", containsString("SameSite=Strict")))
            .andExpect(header().string("Set-Cookie", containsString("Max-Age=86400")));
    }

    @Test
    @DisplayName("로그인 실패시 401 Unauthorized를 반환한다")
    void loginFailTest() throws Exception {
        // given
        AuthRequestDto.LoginRequestDto loginRequest = new AuthRequestDto.LoginRequestDto("wrong@example.com", "wrongpw");

        given(authService.checkEmailAndPassword(any()))
            .willThrow(new UserByEmailNotFoundException());

        // when & then
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isUnauthorized());
    }
}
