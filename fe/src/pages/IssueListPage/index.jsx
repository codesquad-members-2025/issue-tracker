import styled from 'styled-components';
import { useEffect, useState } from 'react';
import useIssuesStore from '@/stores/issuesStore';
import useDataFetch from '@/hooks/useDataFetch';
import { ISSUES_URL } from '@/api/issues';
import MainPageHeaderTap from '@/units/mainPageHeaderTap';
import IsOpenFilter from '@/units/kanbanHeader/IsOpenFilter';
import CheckBox from '@/base-ui/utils/CheckBox';
import useFilterModalStore from '@/stores/detailFilterModalStore';

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
  border-radius: 16px;
  overflow: hidden;
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

const KanbanMain = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  // 최소 높이는 설정 하지 않는다. -> 조건 만족하는 아이템이 없으면 안내 블럭을 띄워주는데 그 블럭이 최소 높이 역할.
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
        <KanbanMain />
      </Kanban>
    </Container>
  );
}
