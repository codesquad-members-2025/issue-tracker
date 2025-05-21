import { type Comment } from '../../types/issue';

interface CommentListProps {
  comments: Comment[];
}

export default function CommentList({ comments }: CommentListProps) {
  return (
    <ul>
      {comments.map((comment: Comment) => (
        <li key={comment.id}>{comment.content}</li>
      ))}
    </ul>
  );
}
