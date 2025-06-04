package elbin_bank.issue_tracker.common.cache.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, Long> userCache() {
        return Caffeine.newBuilder()
                .maximumSize(20_000)
                .expireAfterAccess(Duration.ofMinutes(30))
                .build();
    }

}
