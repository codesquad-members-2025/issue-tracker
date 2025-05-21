import { type Comment } from '../../types/issue';

interface CommentListProps {
  isLoading: boolean;
  isError: boolean;
  comments: Comment[];
}

export default function CommentList({
  comments,
  isError,
  isLoading,
}: CommentListProps) {
  if (isLoading) return <div>댓글 로딩 중...</div>;
  if (isError) return <div>댓글을 불러오는 데 실패했습니다.</div>;

  return (
    <ul>
      {comments.map((comment: Comment) => (
        <li key={comment.id}>{comment.content}</li>
      ))}
    </ul>
  );
}
