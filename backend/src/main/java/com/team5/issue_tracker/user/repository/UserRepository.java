package com.team5.issue_tracker.user.repository;

import com.team5.issue_tracker.user.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

  Optional<User> findByEmail(String email);
  Optional<User> findById(Long id);
  boolean existsById(Long id);
  boolean existsByEmail(String email);
  boolean existsByUsername(String username);
}
