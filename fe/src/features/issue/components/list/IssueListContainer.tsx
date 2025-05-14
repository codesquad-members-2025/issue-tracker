import styled from '@emotion/styled';
import { type IssueStatus } from '../../types/issue';
import useIssues from '../../hooks/useIssues';
import usePreviousWhen from '@/shared/hooks/usePreviousWhen';
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
  const { data: issues, isFetching, isError, error } = useIssues(query);

  const prevIssues = usePreviousWhen(issues?.issues ?? [], !isFetching);
  const prevOpenCount = usePreviousWhen(issues?.openCount ?? 0, !isFetching);
  const prevCloseCount = usePreviousWhen(issues?.closeCount ?? 0, !isFetching);

  const displayIssues = isFetching ? prevIssues : (issues?.issues ?? []);
  const displayOpenCount = isFetching
    ? prevOpenCount
    : (issues?.openCount ?? 0);
  const displayCloseCount = isFetching
    ? prevCloseCount
    : (issues?.closeCount ?? 0);

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
  border: 1px solid ${({ theme }) => theme.borderColor.default};
  border-radius: ${({ theme }) => theme.radius.medium};
  background-color: ${({ theme }) => theme.surfaceColor.default};
  overflow: hidden;

  display: flex;
  flex-direction: column;
`;
