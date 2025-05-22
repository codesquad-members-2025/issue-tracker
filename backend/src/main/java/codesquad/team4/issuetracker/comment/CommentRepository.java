package codesquad.team4.issuetracker.comment;

import codesquad.team4.issuetracker.entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

}
