import styled from '@emotion/styled';
import { type Comment } from '../../types/issue';
import IssueContent from './IssueContent';
import CommentList from './CommentList';
import CommentEditor from './CommentEditor';

interface IssueMainSectionProps {
  isLoading: boolean;
  isError: boolean;
  comments: Comment[];
}

export default function IssueMainSection({
  comments,
  isLoading,
  isError,
}: IssueMainSectionProps) {
  return (
    <MainWrapper>
      <IssueContent />
      <CommentList
        comments={comments}
        isLoading={isLoading}
        isError={isError}
      />
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
