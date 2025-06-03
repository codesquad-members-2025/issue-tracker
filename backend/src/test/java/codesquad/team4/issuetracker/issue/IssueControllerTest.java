package codesquad.team4.issuetracker.issue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import codesquad.team4.issuetracker.auth.AuthInterceptor;
import codesquad.team4.issuetracker.aws.S3FileService;
import codesquad.team4.issuetracker.comment.dto.CommentResponseDto;
import codesquad.team4.issuetracker.exception.badrequest.FileUploadException;
import codesquad.team4.issuetracker.exception.badrequest.IssueStatusUpdateException;
import codesquad.team4.issuetracker.exception.notfound.IssueNotFoundException;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto.ApiMessageDto;
import codesquad.team4.issuetracker.user.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(IssueController.class)
class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IssueService issueService;
    @MockitoBean
    private S3FileService s3FileService;

    @MockitoBean
    private AuthInterceptor authInterceptor;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws IOException {
        given(authInterceptor.preHandle(any(), any(), any())).willReturn(true);
    }

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

        ApiMessageDto responseDto = ApiMessageDto.builder()
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
            .andExpect(jsonPath("$.data.id").value(1L))
            .andExpect(jsonPath("$.data.message").value("이슈가 생성되었습니다."));
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

    @Test
    @DisplayName("이슈 상태를 일괄 변경할 수 있다")
    void bulkUpdateIssueStatus_success() throws Exception {
        // given
        IssueRequestDto.BulkUpdateIssueStatusDto requestDto = IssueRequestDto.BulkUpdateIssueStatusDto.builder()
            .issuesId(List.of(1L, 2L, 3L))
            .isOpen(false)
            .build();

        IssueResponseDto.BulkUpdateIssueStatusDto responseDto = IssueResponseDto.BulkUpdateIssueStatusDto.builder()
            .issuesId(List.of(1L, 2L, 3L))
            .message("이슈 상태가 변경되었습니다.")
            .build();

        given(issueService.bulkUpdateIssueStatus(any())).willReturn(responseDto);

        // when & then
        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch("/api/issues/status")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.issuesId[0]").value(1))
            .andExpect(jsonPath("$.data.issuesId[1]").value(2))
            .andExpect(jsonPath("$.data.issuesId[2]").value(3))
            .andExpect(jsonPath("$.data.message").value("이슈 상태가 변경되었습니다."));
    }

    @Test
    @DisplayName("일부 이슈 ID가 존재하지 않으면 BadRequest를 반환한다")
    void bulkUpdateIssueStatus_failure() throws Exception {
        // given
        IssueRequestDto.BulkUpdateIssueStatusDto requestDto = IssueRequestDto.BulkUpdateIssueStatusDto.builder()
            .issuesId(List.of(1L, 999L))
            .isOpen(false)
            .build();

        given(issueService.bulkUpdateIssueStatus(any()))
            .willThrow(new IssueStatusUpdateException("일부 이슈 ID가 존재하지 않습니다."));

        // when & then
        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch("/api/issues/status")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("일부 이슈 ID가 존재하지 않습니다."));
    }

    @Test
    @DisplayName("존재하지 않는 이슈를 조회할 경우 에러가 발생한다")
    void searchNotPresentIssue() throws Exception {
        // given
        Long notExistIssueId = 999L;

        given(issueService.getIssueDetailById(notExistIssueId))
            .willThrow(new IssueNotFoundException(notExistIssueId));

        // when & then
        mockMvc.perform(get("/api/issues/{issue-id}", notExistIssueId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("이슈를 찾을 수 없습니다. issueId = " + notExistIssueId));
    }

    @Test
    @DisplayName("이슈를 조회할 때 이슈 내용과 댓글을 함께 응답한다")
    void searchIssueDetail() throws Exception {
        // given
        Long issueId = 1L;

        IssueResponseDto.searchIssueDetailDto responseDto =
            IssueResponseDto.searchIssueDetailDto.builder()
                .content("이슈 설명")
                .comments(List.of(
                    CommentResponseDto.CommentInfo.builder()
                        .commentId(1L)
                        .content("댓글1")
                        .author(UserDto.UserInfo.builder()
                            .id(1L)
                            .nickname("작성자1")
                            .build())
                        .build(),
                    CommentResponseDto.CommentInfo.builder()
                        .commentId(2L)
                        .content("댓글2")
                        .author(UserDto.UserInfo.builder()
                            .id(2L)
                            .nickname("작성자2")
                            .build())
                        .build()))
                .build();

        given(issueService.getIssueDetailById(issueId)).willReturn(responseDto);

        // when & then
        mockMvc.perform(get("/api/issues/{issue-id}", issueId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.content").value("이슈 설명"))
            .andExpect(jsonPath("$.data.comments").isArray())
            .andExpect(jsonPath("$.data.comments[0].content").value("댓글1"))
            .andExpect(jsonPath("$.data.comments[0].author.nickname").value("작성자1"))
            .andExpect(jsonPath("$.data.comments[1].content").value("댓글2"))
            .andExpect(jsonPath("$.data.comments[1].author.nickname").value("작성자2"));
    }

}
