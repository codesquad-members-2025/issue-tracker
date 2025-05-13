package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.aws.S3FileService;
import codesquad.team4.issuetracker.exception.FileUploadException;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IssueController.class)
class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IssueService issueService;
    @MockitoBean
    private IssueCountService issueCountService;
    @MockitoBean
    private S3FileService s3FileService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("파일과 이슈 데이터로 이슈를 생성할 수 있다")
    void createIssue_success() throws Exception {
        // given
        IssueRequestDto.CreateIssueDto requestDto = IssueRequestDto.CreateIssueDto.builder()
                .title("이슈 제목")
                .content("이슈 설명")
                .authorId(1L)
                .build();

        String jsonPart = objectMapper.writeValueAsString(requestDto);

        MockMultipartFile jsonFile = new MockMultipartFile(
                "issue", "", "application/json", jsonPart.getBytes());

        MockMultipartFile file = new MockMultipartFile(
                "file", "image.png", "image/png", "image-content".getBytes());

        given(s3FileService.uploadFile(any(), eq("issue/"))).willReturn("https://fake-s3-url/image.png");

        IssueResponseDto.CreateIssueDto responseDto = IssueResponseDto.CreateIssueDto.builder()
                .id(1L)
                .message("이슈가 생성되었습니다.")
                .build();

        given(issueService.createIssue(any(), any())).willReturn(responseDto);

        // when & then
        mockMvc.perform(multipart("/api/issues")
                        .file(jsonFile)
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.message").value("이슈가 생성되었습니다."));
    }

    @Test
    @DisplayName("파일 업로드 실패시 BadRequest를 반환한다")
    void createIssue_fileUploadFail() throws Exception {
        IssueRequestDto.CreateIssueDto requestDto = IssueRequestDto.CreateIssueDto.builder()
                .title("이슈 제목")
                .content("이슈 설명")
                .authorId(1L)
                .build();

        String jsonPart = objectMapper.writeValueAsString(requestDto);

        MockMultipartFile jsonFile = new MockMultipartFile(
                "issue", "", "application/json", jsonPart.getBytes());

        MockMultipartFile file = new MockMultipartFile(
                "file", "image.png", "image/png", "image-content".getBytes());

        given(s3FileService.uploadFile(any(), eq("issues/"))).willThrow(new FileUploadException("파일 업로드에 실패했습니다."));

        mockMvc.perform(multipart("/api/issues")
                        .file(jsonFile)
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("파일 업로드에 실패했습니다."));
    }
}