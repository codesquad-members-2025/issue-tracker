import styled from 'styled-components';
import { useEffect, useState } from 'react';
import { NavigateTabs } from '@/units/mainPageHeaderTap/navigateButtons';
import useIssuesStore from '@/stores/issuesStore';
import useDataFetch from '@/hooks/useDataFetch';
import { ISSUES_URL } from '@/api/issues';
import FilterBar from '@/units/mainPageHeaderTap/FilterBar';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
  padding: 32px 80px;
`;

export default function IssueListPage() {
  const fetchType = '메인 페이지';
  const [page, setPage] = useState(1);
  // 이슈 선택 로직 추후에 구현
  const { response, isLoading, fetchData } = useDataFetch({ fetchType });
  const issues = useIssuesStore((state) => state.issues);
  const setIssues = useIssuesStore((state) => state.setIssues);
  const toggleIssues = useIssuesStore((state) => state.toggleIssues);

  setIssues(response);

  useEffect(() => {
    fetchData(ISSUES_URL);
  }, [page]);
  return (
    <Container>
      <NavigateTabs />
      <FilterBar />
    </Container>
  );
}
