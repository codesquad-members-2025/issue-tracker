package CodeSquad.IssueTracker.issue;

import CodeSquad.IssueTracker.issue.dto.IssueCreateRequest;
import CodeSquad.IssueTracker.issue.dto.IssueDetailResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IssueControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueService issueService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("이슈 생성 통합 테스트 - multipart/form-data 요청")
    void createIssue_multipart_success() throws Exception {
        // given
        IssueCreateRequest request = new IssueCreateRequest();
        request.setTitle("FE 아토믹디자인 시스템 구현");
        request.setContent("이번 그룹 프로젝트에서...");
        request.setAssigneeIds(List.of(1L));
        request.setLabelIds(List.of(10L, 20L));
        request.setMilestoneId(5L);

        String jsonData = objectMapper.writeValueAsString(request);
        MockMultipartFile jsonPart = new MockMultipartFile(
                "data", "data", "application/json", jsonData.getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile filePart = new MockMultipartFile(
                "files", "mock-image.png", "image/png", "mock image content".getBytes()
        );

        IssueDetailResponse fakeResponse = new IssueDetailResponse();
        when(issueService.createIssue(any(), any(), any())).thenReturn(fakeResponse);

        // when
        var result = mockMvc.perform(
                        multipart("/issues")
                                .file(jsonPart)
                                .file(filePart)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .requestAttr("loginId", "testUser")
                )
                .andExpect(status().isOk())
                .andReturn();

        // then
        int status = result.getResponse().getStatus();
        String responseContent = result.getResponse().getContentAsString();

        assertThat(status).isEqualTo(200);
        assertThat(responseContent).isNotNull();
    }
}
