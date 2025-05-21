package codesquad.team4.issuetracker.comment;

import codesquad.team4.issuetracker.aws.S3FileService;
import codesquad.team4.issuetracker.comment.dto.CommentRequestDto;
import codesquad.team4.issuetracker.comment.dto.CommentResponseDto;
import codesquad.team4.issuetracker.exception.badrequest.FileUploadException;
import codesquad.team4.issuetracker.exception.ExceptionMessage;
import codesquad.team4.issuetracker.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CommentResponseDto.CreateCommentDto>> createComment(
            @RequestPart("comment") @Valid CommentRequestDto.CreateCommentDto request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        String uploadUrl;
        try {
            uploadUrl = s3FileService.uploadFile(file, COMMENT_DIRECTORY);
        } catch (FileUploadException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail(ExceptionMessage.FILE_UPLOAD_FAILED));
        }
        CommentResponseDto.CreateCommentDto response = commentService.createComment(request, uploadUrl);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @PatchMapping(value = "/{comment-id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CommentResponseDto.UpdateCommentDto>> updateComment(
            @PathVariable("comment-id") Long commentId,
            @RequestPart("comment") @Valid CommentRequestDto.UpdateCommentDto request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        String uploadUrl;
        try {
            uploadUrl = s3FileService.uploadFile(file, COMMENT_DIRECTORY);
        } catch (FileUploadException e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(ExceptionMessage.FILE_UPLOAD_FAILED));
        }

        CommentResponseDto.UpdateCommentDto response = commentService.updateComment(commentId, request, uploadUrl);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
