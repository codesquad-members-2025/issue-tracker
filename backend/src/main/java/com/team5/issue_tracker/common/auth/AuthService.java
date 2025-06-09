package com.team5.issue_tracker.common.auth;

import org.springframework.stereotype.Service;

import com.team5.issue_tracker.common.exception.ErrorCode;
import com.team5.issue_tracker.common.exception.NotFoundException;
import com.team5.issue_tracker.common.exception.UnauthorizedException;
import com.team5.issue_tracker.user.domain.User;
import com.team5.issue_tracker.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;

  public LoginResponse login(LoginRequest loginRequest) {
    User user = userRepository.findByEmail(loginRequest.getEmail())
        .orElseThrow(
            () -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "해당 이메일을 가진 사용자가 없습니다."));

    if (!user.getPassword().equals(loginRequest.getPassword())) {
      throw new UnauthorizedException(ErrorCode.INVALID_PASSWORD);
    }

    String accessToken = jwtTokenProvider.createToken(user.getId(), user.getUsername());

    return new LoginResponse(accessToken);
  }

  public void signup(SignupRequest signupRequest) {
    if (userRepository.existsByEmail(signupRequest.getEmail())) {
      throw new UnauthorizedException(ErrorCode.EMAIL_ALREADY_EXISTS);
    }

    if (userRepository.existsByUsername(signupRequest.getUsername())) {
      throw new UnauthorizedException(ErrorCode.USERNAME_ALREADY_EXISTS);
    }

    User user = new User(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword(),
            signupRequest.getImageUrl());

    userRepository.save(user);
  }
}
