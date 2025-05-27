package com.team5.issue_tracker.common.comment.reqository;

import org.springframework.data.repository.CrudRepository;
import com.team5.issue_tracker.common.comment.domain.CommentAttachment;

public interface CommentAttachmentRepository extends CrudRepository<CommentAttachment, Long> {
}
