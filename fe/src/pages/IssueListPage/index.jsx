import styled from 'styled-components';
import { useEffect, useState } from 'react';
import useIssuesStore from '@/stores/issuesStore';
import useDataFetch from '@/hooks/useDataFetch';
import { ISSUES_URL } from '@/api/issues';
import MainPageHeaderTap from '@/units/mainPageHeaderTap';
import IsOpenFilter from '@/units/kanbanHeader/IsOpenFilter';
import CheckBox from '@/base-ui/utils/CheckBox';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
  padding: 32px 80px;
`;
const Kanban = styled.div`
  border: 1px solid ${({ theme }) => theme.border.default};
  display: flex;
  flex-direction: column;
`;

const KanbanHeader = styled.div`
  padding: 16px 32px;
  width: 100%;
  height: 64px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: ${({ theme }) => theme.surface.default};
`;

const HeaderLeft = styled.div`
  display: flex;
  gap: 32px;
  align-items: center;
`;
const HeaderRight = styled.div`
  display: flex;
  gap: 32px;
`;

function useIssueStore() {
  const issues = useIssuesStore((state) => state.issues);
  const setIssues = useIssuesStore((state) => state.setIssues);
  const toggleIssues = useIssuesStore((state) => state.toggleIssues);
  const parseIssue = useIssuesStore((state) => state.parseIssue);
  const issueSummary = useIssuesStore((state) => state.issueSummary);
  return { issues, setIssues, toggleIssues, parseIssue, issueSummary };
}

export default function IssueListPage() {
  const fetchType = '메인 페이지';
  const [page, setPage] = useState(1); // 필터 스토어에서 관리해야함!!
  // 이슈 선택 로직 추후에 구현
  const { response, isLoading, fetchData } = useDataFetch({ fetchType });
  const { issues, setIssues, toggleIssues, parseIssue, issueSummary } = useIssueStore();

  useEffect(() => {
    fetchData(ISSUES_URL);
  }, [page]);
  useEffect(() => {
    if (response) {
      setIssues(response); // ✅ 상태 변화 감지해서 후처리
      parseIssue(); // 여기도 safe하게
    }
  }, [response]);

  return (
    <Container>
      <MainPageHeaderTap />
      <Kanban>
        <KanbanHeader>
          <HeaderLeft>
            <CheckBox isDisabled={true} />
            <IsOpenFilter
              openIssueNumber={issueSummary.openIssueNumber}
              closeIssueNumber={issueSummary.closeIssueNumber}
            />
          </HeaderLeft>
          <HeaderRight></HeaderRight>
        </KanbanHeader>
      </Kanban>
    </Container>
  );
}
