package CodeSquad.IssueTracker.comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    Optional<Comment> findById(Long commentId);
    List<Comment> findAll();
    void deleteById(Long commentId);
    void update(Long commentId, CommentUpdateDto updateDto);
}
