package CodeSquad.IssueTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IssueTrackerApplication {

	public static void main(String[] args) {
		// EC2 환경변수 → Spring이 이해하는 형식으로 강제 주입
		String accessKey = System.getenv("JWT_ACCESS_KEY");
		String refreshKey = System.getenv("JWT_REFRESH_KEY");
		String awsAccessKey = System.getenv("AWS_ACCESS_KEY_ID");
		String awsSecretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
		String awsRegion = System.getenv("AWS_REGION");
		String s3Bucket = System.getenv("S3_BUCKET_NAME");

		if (accessKey != null) System.setProperty("spring.jwt.access-key", accessKey);
		if (refreshKey != null) System.setProperty("spring.jwt.refresh-key", refreshKey);
		if (awsAccessKey != null) System.setProperty("spring.cloud.aws.credentials.access-key", awsAccessKey);
		if (awsSecretKey != null) System.setProperty("spring.cloud.aws.credentials.secret-key", awsSecretKey);
		if (awsRegion != null) System.setProperty("spring.cloud.aws.region.static", awsRegion);
		if (s3Bucket != null) System.setProperty("spring.cloud.aws.s3.bucket", s3Bucket);

		SpringApplication.run(IssueTrackerApplication.class, args);
	}
}
