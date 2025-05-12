package CodeSquad.IssueTracker.issue;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class IssueServiceTest {

    @Autowired
    private IssueService issueService;

    @Test
    @DisplayName("이슈 저장 후 조회")
    void saveAndFindById() {
        // given
        Issue issue = new Issue();
        issue.setTitle("test issue");
        issue.setIsOpen(true);
        issue.setCreatedAt(LocalDateTime.now());
        issue.setAuthorId(1L);
        issue.setMilestoneId(1L);

        // when
        Issue saved = issueService.save(issue);
        Optional<Issue> found = issueService.findById(saved.getIssueId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("test issue");
    }
}