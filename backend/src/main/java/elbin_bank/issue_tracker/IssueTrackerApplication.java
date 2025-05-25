package elbin_bank.issue_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class IssueTrackerApplication {

    public static void main(String[] args) {
        // 전체 JVM의 기본 타임존을 UTC로 설정
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(IssueTrackerApplication.class, args);
    }

}
