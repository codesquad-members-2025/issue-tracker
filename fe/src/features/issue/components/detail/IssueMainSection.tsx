import styled from '@emotion/styled';
import IssueContent from './IssueContent';
import CommentList from './CommentList';
import CommentEditor from './CommentEditor';

interface IssueMainSectionProps {
  issueId: number;
}

export default function IssueMainSection({ issueId }: IssueMainSectionProps) {
  return (
    <MainWrapper>
      <IssueContent />
      <CommentList issueId={issueId} />
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
