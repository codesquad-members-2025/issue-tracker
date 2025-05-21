import styled from '@emotion/styled';
import { type Comment, type CommentAuthor } from '../../types/issue';
import IssueDescription from './IssueDescription';
import CommentList from './CommentList';
import CommentEditor from './CommentEditor';

interface IssueMainSectionProps {
  comments: Comment[];
  content: string;
  createdAt: string;
  author: CommentAuthor;
}

export default function IssueMainSection({
  comments,
  content,
  createdAt,
  author,
}: IssueMainSectionProps) {
  return (
    <MainWrapper>
      <IssueDescription
        content={content}
        createdAt={createdAt}
        author={author}
      />
      {comments.length > 0 && <CommentList comments={comments} />}
      <CommentEditor />
    </MainWrapper>
  );
}

const MainWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
  flex: 1;
`;
