import styled from '@emotion/styled';
import { type Comment } from '../../types/issue';
import CommentDescription from './CommentDescription';

interface CommentListProps {
  comments: Comment[];
}

export default function CommentList({ comments }: CommentListProps) {
  return (
    <Wrapper>
      {comments.map((comment: Comment) => (
        <CommentDescription
          key={comment.id}
          content={comment.content}
          author={comment.author}
          createdAt={comment.createdAt}
        />
      ))}
    </Wrapper>
  );
}

const Wrapper = styled.ul`
  display: flex;
  gap: 24px;
  flex-direction: column;
`;
