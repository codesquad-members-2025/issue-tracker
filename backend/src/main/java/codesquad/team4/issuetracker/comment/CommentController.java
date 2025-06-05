package codesquad.team4.issuetracker.comment;

import codesquad.team4.issuetracker.auth.AuthInterceptor;
import codesquad.team4.issuetracker.aws.S3FileService;
import codesquad.team4.issuetracker.comment.dto.CommentRequestDto;
import codesquad.team4.issuetracker.comment.dto.CommentResponseDto;
import codesquad.team4.issuetracker.entity.User;
import codesquad.team4.issuetracker.response.ApiResponse;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/issues/comments")
public class CommentController {
    private static final String COMMENT_DIRECTORY = "comment/";

    private final CommentService commentService;
    private final S3FileService s3FileService;
    private final ServletRequest servletRequest;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<CommentResponseDto.CreateCommentDto> createComment(
            @RequestPart("comment") @Valid CommentRequestDto.CreateCommentDto request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        String uploadUrl = s3FileService.uploadFile(file, COMMENT_DIRECTORY);

        CommentResponseDto.CreateCommentDto response = commentService.createComment(request, uploadUrl);
        return ApiResponse.success(response);
    }

    @PatchMapping(value = "/{comment-id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<CommentResponseDto.UpdateCommentDto> updateComment(
        @PathVariable("comment-id") Long commentId,
        @RequestPart("comment") @Valid CommentRequestDto.UpdateCommentDto request,
        @RequestPart(value = "file", required = false) MultipartFile file,
        HttpServletRequest servletRequest) {

        String uploadUrl = s3FileService.uploadFile(file, COMMENT_DIRECTORY);
        User user = (User) servletRequest.getAttribute(AuthInterceptor.USER_ATTRIBUTE);

        CommentResponseDto.UpdateCommentDto response = commentService.updateComment(commentId, request, uploadUrl, user);
        return ApiResponse.success(response);
    }

    @DeleteMapping("/{issue-id}/{comment-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("issue-id") Long issueId,
                              @PathVariable("comment-id") Long commentId,
                              HttpServletRequest servletRequest) {

        User user = (User) servletRequest.getAttribute(AuthInterceptor.USER_ATTRIBUTE);
        commentService.deleteComment(issueId, commentId, user);
    }
}
