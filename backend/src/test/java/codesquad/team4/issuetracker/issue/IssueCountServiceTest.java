package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.issue.dto.IssueCountDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class IssueCountServiceTest {

    @Mock
    private IssueDao issueDao;

    @InjectMocks
    private IssueService issueService;

    @Test
    @DisplayName("이슈 상태별 개수 반환")
    void 이슈_상태별_개수_반환() {
        // given
        given(issueDao.countIssuesByOpenStatus(true)
        ).willReturn(2);

        given(issueDao.countIssuesByOpenStatus(false)
        ).willReturn(1);

        // when
        IssueCountDto result = issueService.getIssueCounts();

        // then
        assertThat(result.getOpenCount()).isEqualTo(2);
        assertThat(result.getClosedCount()).isEqualTo(1);
    }
}
