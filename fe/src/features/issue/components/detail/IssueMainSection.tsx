import styled from '@emotion/styled';
import IssueContent from './IssueContent';
import CommentList from './CommentList';
import CommentEditor from './CommentEditor';

export default function IssueMainSection() {
  return (
    <MainWrapper>
      <IssueContent />
      <CommentList />
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
