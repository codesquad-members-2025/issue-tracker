package CodeSquad.IssueTracker.jwt.filter;

import CodeSquad.config.SimpleCORSFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public FilterConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public FilterRegistrationBean<SimpleCORSFilter> corsFilter() {
        FilterRegistrationBean<SimpleCORSFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SimpleCORSFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(0); // CORS 필터가 제일 먼저 실행됨
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtFilter() {
        FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtAuthFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1); // 두 번째로 실행됨
        return registrationBean;
    }
}
