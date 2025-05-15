import styled from 'styled-components';
import { useEffect, useState } from 'react';
import useIssuesStore from '@/stores/issuesStore';
import useDataFetch from '@/hooks/useDataFetch';
import { ISSUES_URL } from '@/api/issues';
import MainPageHeaderTap from '@/units/mainPageHeaderTap';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
  padding: 32px 80px;
`;
const Kanban = styled.div`
  border: 1px solid ${({ theme }) => theme.border.default};
  width: 1280px;
  display: flex;
  flex-direction: column;
  padding: 16px 32px;
`;

export default function IssueListPage() {
  const fetchType = '메인 페이지';
  const [page, setPage] = useState(1); // 필터 스토어에서 관리해야함!!
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
      <MainPageHeaderTap />
      <Kanban></Kanban>
    </Container>
  );
}
