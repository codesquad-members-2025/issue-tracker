package com.team5.issue_tracker.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class User {

    @Id
    private Long id;

    private final String username;
    private final String email;
    private final String password;
}
