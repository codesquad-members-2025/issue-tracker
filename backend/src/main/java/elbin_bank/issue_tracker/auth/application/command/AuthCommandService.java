package elbin_bank.issue_tracker.auth.application.command;

import com.github.benmanes.caffeine.cache.Cache;
import elbin_bank.issue_tracker.auth.application.command.dto.TokenResponseDto;
import elbin_bank.issue_tracker.auth.domain.JwtProvider;
import elbin_bank.issue_tracker.auth.exception.InvalidPasswordException;
import elbin_bank.issue_tracker.auth.exception.UserAlreadyExistsException;
import elbin_bank.issue_tracker.auth.presentation.command.dto.LoginRequestDto;
import elbin_bank.issue_tracker.auth.presentation.command.dto.SignUpRequestDto;
import elbin_bank.issue_tracker.auth.util.PasswordEncoderUtil;
import elbin_bank.issue_tracker.common.exception.EntityNotFoundException;
import elbin_bank.issue_tracker.user.domain.User;
import elbin_bank.issue_tracker.user.domain.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthCommandService {

    private final UserCommandRepository userCommandRepository;
    private final JwtProvider jwt;
    private final Cache<String, Long> cache;

    @Transactional
    public void register(SignUpRequestDto dto) {
        Optional<User> user = userCommandRepository.findByLogin(dto.login());

        if (user.isPresent()) {
            throw new UserAlreadyExistsException("이미 존재하는 사용자입니다.");
        }

        if (user.get().getNickname().equals(dto.nickname())) {
            throw new UserAlreadyExistsException("이미 존재하는 닉네임입니다.");
        }

        String uuid = UUID.randomUUID().toString();
        String salt = PasswordEncoderUtil.generateSalt();
        String hash = PasswordEncoderUtil.hashPassword(dto.password(), salt);

        User newUser = User.createByLogin(dto.login(), hash, salt, dto.nickname(), dto.profileImageUrl(), uuid);

        userCommandRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    public TokenResponseDto login(LoginRequestDto dto) {
        User user = userCommandRepository.findByLogin(dto.login())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        if (!PasswordEncoderUtil.verifyPassword(dto.password(), user.getSalt(), user.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
        // 캐시에 사용자 UUID와 ID를 저장
        cache.put(user.getUuid(), user.getId());

        // JWT 생성 및 반환
        return jwt.createJwt(user);
    }

}
