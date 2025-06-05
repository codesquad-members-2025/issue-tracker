package codesquad.team4.issuetracker.comment;

import codesquad.team4.issuetracker.comment.dto.CommentRequestDto;
import codesquad.team4.issuetracker.comment.dto.CommentResponseDto;
import codesquad.team4.issuetracker.entity.Comment;
import codesquad.team4.issuetracker.entity.User;
import codesquad.team4.issuetracker.exception.notfound.CommentNotFoundException;
import codesquad.team4.issuetracker.exception.unauthorized.NotAuthorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private CommentRequestDto.CreateCommentDto.CreateCommentDtoBuilder requestDtoBuilder;
    private User author;

    @BeforeEach
    public void setUp() {
        requestDtoBuilder = CommentRequestDto.CreateCommentDto.builder()
                .content("Test Content")
                .issueId(1L)
                .authorId(1L);

        author = User.builder()
            .id(1L)
            .email("author@test.com")
            .nickname("작성자")
            .build();
    }

    @Test
    @DisplayName("댓글 생성 성공 - 이미지 포함")
    void testCreateComment_WithImage() {
        // given
        CommentRequestDto.CreateCommentDto requestDto = requestDtoBuilder.build();
        String uploadUrl = "http://example.com/uploaded_image.jpg";

        Comment comment = Comment.builder()
                .id(1L)
                .content("Test Test")
                .fileUrl(uploadUrl)
                .authorId(1L)
                .issueId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(commentRepository.save(any(Comment.class))).willReturn(comment);

        // when
        CommentResponseDto.CreateCommentDto response = commentService.createComment(requestDto, uploadUrl);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getMessage()).isEqualTo("댓글이 생성되었습니다.");

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 생성 성공 - 이미지 없이")
    void testCreateComment_WithOutImage() {
        //given
        CommentRequestDto.CreateCommentDto requestDto = requestDtoBuilder.build();
        String uploadUrl = "";

        Comment comment = Comment.builder()
                .id(2L)
                .content("댓글 내용")
                .fileUrl(uploadUrl)
                .authorId(1L)
                .issueId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(commentRepository.save(any(Comment.class))).willReturn(comment);

        // when
        CommentResponseDto.CreateCommentDto response = commentService.createComment(requestDto, uploadUrl);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getMessage()).isEqualTo("댓글이 생성되었습니다.");

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 수정 성공")
    void updateComment_success() {
        // given
        Long commentId = 1L;
        Comment existingComment = Comment.builder()
                .id(commentId)
                .issueId(10L)
                .authorId(1L)
                .content("이전 댓글")
                .fileUrl("old.png")
                .createdAt(LocalDateTime.now())
                .build();

        given(commentRepository.findById(commentId)).willReturn(Optional.of(existingComment));

        CommentRequestDto.UpdateCommentDto request = CommentRequestDto.UpdateCommentDto.builder()
                .content("수정된 댓글")
                .build();

        String uploadUrl = "https://s3.com/new.png";

        // when
        CommentResponseDto.UpdateCommentDto result = commentService.updateComment(commentId, request, uploadUrl, author);

        // then
        assertThat(result.getId()).isEqualTo(commentId);
        assertThat(result.getMessage()).isEqualTo("댓글이 수정되었습니다.");
        assertThat(existingComment.getContent()).isEqualTo("수정된 댓글");
        assertThat(existingComment.getFileUrl()).isEqualTo(uploadUrl);
    }

    @Test
    @DisplayName("댓글 수정 실패 - 댓글 없음")
    void updateComment_notFound() {
        // given
        Long commentId = 100L;
        given(commentRepository.findById(commentId)).willReturn(Optional.empty());

        CommentRequestDto.UpdateCommentDto request = CommentRequestDto.UpdateCommentDto.builder()
                .content("수정된 댓글")
                .build();

        // when + then
        assertThatThrownBy(() ->
                commentService.updateComment(commentId, request, "dummy.png", author)
        ).isInstanceOf(CommentNotFoundException.class)
                .hasMessageContaining("댓글을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void testDeleteComment_success() {
        // given
        Long issueId = 1L;
        Long commentId = 2L;

        Comment comment = Comment.builder()
                .id(commentId)
                .issueId(issueId)
                .authorId(1L)
                .content("삭제할 댓글.")
                .createdAt(LocalDateTime.now())
                .build();

        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

        // when
        commentService.deleteComment(issueId, commentId, author);

        // then
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    @DisplayName("존재하지 않는 댓글을 삭제하려 하면 에러가 발생한다")
    void deleteNotExistComment() {
        //given
        Long issueId = 1L;
        Long commentId = 999L;

        //when & then
        assertThatThrownBy(() -> commentService.deleteComment(issueId, commentId, author))
                .isInstanceOf(CommentNotFoundException.class);
    }

    @Test
    @DisplayName("댓글 수정 실패 - 작성자가 아닌 사용자가 수정 시도할 경우 예외 발생")
    void updateComment_notAuthor_shouldThrowException() {
        // given
        Long commentId = 1L;

        Comment existingComment = Comment.builder()
            .id(commentId)
            .issueId(10L)
            .authorId(1L)
            .content("이전 댓글")
            .fileUrl("old.png")
            .createdAt(LocalDateTime.now())
            .build();

        User otherUser = User.builder()
            .id(999L)
            .email("not-author@test.com")
            .nickname("너누구야")
            .build();

        given(commentRepository.findById(commentId)).willReturn(Optional.of(existingComment));

        CommentRequestDto.UpdateCommentDto request = CommentRequestDto.UpdateCommentDto.builder()
            .content("수정되면안된다잉")
            .build();

        // when & then
        assertThatThrownBy(() ->
            commentService.updateComment(commentId, request, "new.png", otherUser)
        ).isInstanceOf(NotAuthorException.class);
    }

    @Test
    @DisplayName("댓글 삭제 실패 - 작성자가 아닌 사용자가 삭제 시도할 경우 예외 발생")
    void deleteComment_notAuthor_shouldThrowException() {
        // given
        Long issueId = 1L;
        Long commentId = 1L;

        Comment comment = Comment.builder()
            .id(commentId)
            .issueId(issueId)
            .authorId(1L)
            .content("삭제할 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        User otherUser = User.builder()
            .id(999L)
            .email("not-author@test.com")
            .nickname("니누고")
            .build();

        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

        // when & then
        assertThatThrownBy(() ->
            commentService.deleteComment(issueId, commentId, otherUser)
        ).isInstanceOf(NotAuthorException.class);
    }

}
