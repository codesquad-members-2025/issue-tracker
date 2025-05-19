package elbin_bank.issue_tracker.issue.presentation;

import elbin_bank.issue_tracker.issue.application.query.GetFilteredIssueListService;
import elbin_bank.issue_tracker.issue.application.query.dto.IssueSummaryDto;
import elbin_bank.issue_tracker.issue.application.query.dto.IssuesResponseDto;
import elbin_bank.issue_tracker.label.application.query.dto.LabelsDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IssueQueryController.class)
class IssueQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private GetFilteredIssueListService getFilteredIssueListService;

    @Test
    @DisplayName("GET /api/v1/issues?q=쿼리 → 200 OK 응답 맡 state:oepn인 목록 반환")
    void list_success() throws Exception {
        // given
        IssueSummaryDto dto = new IssueSummaryDto(
                1L,
                "작성자1",
                "이슈 제목",
                List.of(new LabelsDto(1L, "bug", "f00", "버그")),
                false,
                LocalDateTime.now(),
                LocalDateTime.now(),
                List.of("https://profile1.png", "https://profile2.png"),
                "1차 마일스톤"
        );
        IssuesResponseDto response = new IssuesResponseDto(List.of(dto), 1, 0);

        when(getFilteredIssueListService.find(anyString())).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/v1/issues")
                        .param("q", "state:open"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.issues[0].id").value(1))
                .andExpect(jsonPath("$.issues[0].author").value("작성자1"))
                .andExpect(jsonPath("$.issues[0].title").value("이슈 제목"))
                .andExpect(jsonPath("$.issues[0].labels[0].name").value("bug"))
                .andExpect(jsonPath("$.issues[0].isClosed").value(false))
                .andExpect(jsonPath("$.issues[0].assigneesProfileImages[0]").value("https://profile1.png"))
                .andExpect(jsonPath("$.issues[0].milestone").value("1차 마일스톤"));
    }

}
