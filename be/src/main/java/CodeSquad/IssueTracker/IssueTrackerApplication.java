package CodeSquad.IssueTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IssueTrackerApplication {

	public static void main(String[] args) {
		String accessKey = System.getenv("JWT_ACCESS_KEY");
		String refreshKey = System.getenv("JWT_REFRESH_KEY");

		if (accessKey != null) System.setProperty("JWT_ACCESS_KEY", accessKey);
		if (refreshKey != null) System.setProperty("JWT_REFRESH_KEY", refreshKey);

		SpringApplication.run(IssueTrackerApplication.class, args);
	}

}
