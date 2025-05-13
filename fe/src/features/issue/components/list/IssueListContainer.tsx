import styled from '@emotion/styled';
import { type IssueStatus } from '../../types/issue';
import IssueListHeader from './IssueListHeader';
import IssueListContent from './IssueListContent';
import ISSUES_MOCK from '@/mocks/issuesMock'; //TODO fetch 기능 생성후 변경

interface IssueListContainerProps {
  selected: IssueStatus;
  onChangeTab: (status: IssueStatus) => void;
}

export default function IssueListContainer({
  onChangeTab,
  selected,
}: IssueListContainerProps) {
  return (
    <Container>
      <IssueListHeader
        selected={selected}
        onChangeTab={onChangeTab}
        openCount={ISSUES_MOCK.openCount}
        closeCount={ISSUES_MOCK.closeCount}
      />
      <IssueListContent issues={ISSUES_MOCK.issues} />
    </Container>
  );
}

const Container = styled.section`
  border: 1px solid ${({ theme }) => theme.borderColor.default};
  border-radius: ${({ theme }) => theme.radius.medium};
  background-color: ${({ theme }) => theme.surfaceColor.default};
  overflow: hidden;

  display: flex;
  flex-direction: column;
`;
