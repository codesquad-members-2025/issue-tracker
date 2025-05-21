import useIssueComments from '../../hooks/useIssueComments';
import { type Comment } from '../../types/issue';

interface CommentListProps {
  issueId: number;
}

export default function CommentList({ issueId }: CommentListProps) {
  const { data, isLoading, isError } = useIssueComments(issueId);

  //TODO 에러나 로딩에 따른 구체적인 분기처리 필요
  if (isLoading) return <div>로딩 중...</div>;
  if (isError) return <div>댓글 불러오기 실패</div>;

  return (
    <ul>
      {data?.map((comment: Comment) => (
        <li key={comment.id}>{comment.content}</li>
      ))}
    </ul>
  );
}
