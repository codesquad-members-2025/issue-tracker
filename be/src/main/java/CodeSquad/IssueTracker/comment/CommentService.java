package CodeSquad.IssueTracker.comment;

import CodeSquad.IssueTracker.comment.dto.CommentRequestDto;
import CodeSquad.IssueTracker.comment.dto.CommentResponseDto;
import CodeSquad.IssueTracker.comment.dto.CommentUpdateDto;
import CodeSquad.IssueTracker.global.exception.UserNotFoundException;
import CodeSquad.IssueTracker.user.User;
import CodeSquad.IssueTracker.user.UserRepository;
import CodeSquad.IssueTracker.util.Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final Uploader s3Uploader;

    public CommentResponseDto createComment(CommentRequestDto dto, List<MultipartFile> files,Long authorId) throws IOException {
        Comment comment = Comment.createComment(dto.getIssueId(), dto.getContent(), authorId);

        if(files != null && !files.isEmpty()){
            MultipartFile file = files.getFirst();
            String issueFileUrl = s3Uploader.upload(file);
            comment.setIssueFileUrl(issueFileUrl);
        }

        commentRepository.save(comment);

        Optional<User> author = userRepository.findById(authorId);
        return new CommentResponseDto(comment,author.get()); //이중으로 null 체크를 해야할까?
    }

    public Iterable<Comment> findAll() {
        return commentRepository.findAll();
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public CommentResponseDto update(Long commentId, CommentUpdateDto dto,  List<MultipartFile> files,Long requesterId) throws IOException {
        Comment comment = commentRepository.findById(commentId).get(); //To do 이중으로 null 체크를 해야할까?

        if(files != null && !files.isEmpty()){
            MultipartFile file = files.getFirst();
            String issueFileUrl = s3Uploader.upload(file);
            comment.setIssueFileUrl(issueFileUrl);
        }

        comment.update(dto.getContent(), requesterId );
        return new CommentResponseDto(comment,userRepository.findById(requesterId).get());
    }

    public List<Comment> findByIssueId(Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }

    public List<CommentResponseDto> findCommentResponsesByIssueId(Long issueId) {
        List<Comment> comments = commentRepository.findByIssueId(issueId);

        return comments.stream()
                .map(comment -> {
                    User author = userRepository.findById(comment.getAuthorId())
                            .orElseThrow(UserNotFoundException::new);
                    return new CommentResponseDto(comment, author);
                })
                .toList();
    }


}
