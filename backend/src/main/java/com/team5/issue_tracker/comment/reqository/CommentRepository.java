package com.team5.issue_tracker.comment.reqository;

import org.springframework.data.repository.CrudRepository;
import com.team5.issue_tracker.comment.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
