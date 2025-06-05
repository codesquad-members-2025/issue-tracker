package com.team5.issue_tracker.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.team5.issue_tracker.common.auth.JwtAuthFilter;
import com.team5.issue_tracker.common.auth.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

  private final JwtTokenProvider jwtTokenProvider;

  @Bean
  public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilter() {
    FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new JwtAuthFilter(jwtTokenProvider));
    registrationBean.addUrlPatterns("/*"); // 모든 요청에 적용
    registrationBean.setOrder(1); // 필터 순서 (낮을수록 먼저 실행됨)
    return registrationBean;
  }
}
