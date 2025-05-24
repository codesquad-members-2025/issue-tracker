import styled from '@emotion/styled';
import { type IssueStatus } from '../../types/issue';
import useIssues from '../../hooks/useIssues';
import { buildIssueQuery } from '../../utils';
import IssueListHeader from './IssueListHeader';
import IssueListContent from './IssueListContent';

interface IssueListContainerProps {
  selected: IssueStatus;
  onChangeTab: (status: IssueStatus) => void;
}

export default function IssueListContainer({
  selected,
  onChangeTab,
}: IssueListContainerProps) {
  const query = buildIssueQuery(selected);
  const { issueList, openCount, closeCount, isFetching, isError, error } =
    useIssues(query);

  const displayIssues = issueList ?? [];
  const displayOpenCount = openCount ?? 0;
  const displayCloseCount = closeCount ?? 0;

  if (isError) {
    console.error('[이슈 목록 로딩 에러]', error);
  }

  return (
    <Container>
      <IssueListHeader
        selected={selected}
        onChangeTab={onChangeTab}
        openCount={displayOpenCount}
        closeCount={displayCloseCount}
      />
      <IssueListContent
        issues={displayIssues}
        isFetching={isFetching}
        isError={isError}
      />
    </Container>
  );
}

const Container = styled.section`
  border: 1px solid ${({ theme }) => theme.neutral.border.default};
  border-radius: ${({ theme }) => theme.radius.medium};
  background-color: ${({ theme }) => theme.neutral.surface.default};
  overflow: hidden;

  display: flex;
  flex-direction: column;
`;
