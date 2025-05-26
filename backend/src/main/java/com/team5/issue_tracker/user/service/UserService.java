package com.team5.issue_tracker.user.service;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public Boolean existsById(Long userId) {
    return userRepository.existsById(userId);
  }
}
