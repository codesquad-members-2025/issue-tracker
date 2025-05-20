package codesquad.team01.issuetracker.common.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@Configuration
@EnableJdbcAuditing
public class DataJdbcConfig {

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of("system");
	}
}
