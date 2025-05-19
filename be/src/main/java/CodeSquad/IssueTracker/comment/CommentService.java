package CodeSquad.IssueTracker.comment;

import CodeSquad.IssueTracker.comment.dto.CommentRequestDto;
import CodeSquad.IssueTracker.comment.dto.CommentResponseDto;
import CodeSquad.IssueTracker.comment.dto.CommentUpdateDto;
import CodeSquad.IssueTracker.user.User;
import CodeSquad.IssueTracker.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentResponseDto createComment(CommentRequestDto dto, Long authorId) {
        Comment comment = Comment.createComment(dto.getIssueId(), dto.getContent(), authorId, dto.getImageUrl());
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

    public CommentResponseDto update(Long commentId, CommentUpdateDto dto, Long requesterId) throws AccessDeniedException {
        Comment comment = commentRepository.findById(commentId).get(); //To do 이중으로 null 체크를 해야할까?
        comment.update(dto.getContent(), dto.getImageUrl(), requesterId );
        return new CommentResponseDto(comment,userRepository.findById(requesterId).get());
    }

    public List<Comment> findByIssueId(Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }


}
