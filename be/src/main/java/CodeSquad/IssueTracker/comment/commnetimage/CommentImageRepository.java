package CodeSquad.IssueTracker.comment.commnetimage;

public interface CommentImageRepository {
    CommentImage save(CommentImage commentImage);
    void deleteByCommentId(Long commentId);
    CommentImage findByCommentId(Long commentId);
}
