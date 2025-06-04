package elbin_bank.issue_tracker.common.config;

import com.github.benmanes.caffeine.cache.Cache;
import elbin_bank.issue_tracker.auth.domain.JwtProvider;
import elbin_bank.issue_tracker.auth.presentation.filter.JwtAuthenticationFilter;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class WebConfig {

    private final JwtProvider jwt;
    private final Cache<String, Long> userCache;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public FilterRegistrationBean<Filter> jwtFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();

        // 1) JwtAuthenticationFilter 인스턴스 생성
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwt, userCache);

        // 2) 필터 등록
        registration.setFilter(jwtFilter);

        // 3) 모든 경로("/*")에 대해 필터가 동작하도록 설정
        registration.addUrlPatterns("/api/*");

        // 4) 순서를 가장 높게 설정 (가장 먼저 실행)
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return registration;
    }

}
