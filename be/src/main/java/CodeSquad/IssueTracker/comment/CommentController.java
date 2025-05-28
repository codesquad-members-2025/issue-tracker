package CodeSquad.IssueTracker.comment;

import CodeSquad.IssueTracker.comment.dto.CommentRequestDto;
import CodeSquad.IssueTracker.comment.dto.CommentResponseDto;
import CodeSquad.IssueTracker.comment.dto.CommentUpdateDto;
import CodeSquad.IssueTracker.issue.dto.IssueCreateRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/issues/{issueId}/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommentResponseDto writeComment(@RequestPart("data") @Validated CommentRequestDto request,
                                           @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                           HttpServletRequest httpRequest) throws IOException {
        String authorId = httpRequest.getAttribute("id").toString();
        log.info("Writing comment with author id {}", authorId);
        return commentService.createComment(request,files, Long.valueOf(authorId));
    }

    @PatchMapping("/{commentId}")
    public CommentResponseDto updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentUpdateDto request
                                            ,@RequestPart(value = "files", required = false) List<MultipartFile> files,
     HttpServletRequest httpRequest) throws IOException {
        String authorId = httpRequest.getAttribute("id").toString();
        return commentService.update(commentId,request,files, Long.valueOf(authorId));
    }


}
