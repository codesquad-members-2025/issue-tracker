import styled from 'styled-components';
import { useEffect, useState } from 'react';
import useIssuesStore from '@/stores/issuesStore';
import useDataFetch from '@/hooks/useDataFetch';
import { ISSUES_URL } from '@/api/issues';
import { TEST_ISSUES_URL } from '@/api/issues';
import MainPageHeaderTap from '@/units/mainPageHeaderTap';
import IsOpenFilter from '@/units/kanbanHeader/IsOpenFilter';
import CheckBox from '@/base-ui/utils/CheckBox';
import useFilterModalStore from '@/stores/detailFilterModalStore';
import useFilterStore from '@/stores/filterStore';
import DetailFilterModal from '@/units/detailFilterModal';
import { useRef } from 'react';
import DetailFilterTriigerButton from '@/units/detailFilterModal/DetailFilterTriigerButton';
import { useLocation } from 'react-router-dom';
import { useApplyQueryParams } from '@/utils/queryParams/useApplyQueryParams';

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

export default function IssueListPage() {
  const fetchType = '메인 페이지';
  // 이슈 선택 로직 추후에 구현
  const { response, isLoading, fetchData } = useDataFetch({ fetchType });

  const issues = useIssuesStore((state) => state.issues);
  const setIssues = useIssuesStore((state) => state.setIssues);
  const toggleIssues = useIssuesStore((state) => state.toggleIssues);
  const setMetaData = useIssuesStore((state) => state.setMetaData);
  const metaData = useIssuesStore((state) => state.metaData); // 페이지네이션에 필요
  const setFilterData = useFilterModalStore((state) => state.setFilterData);
  const isActive = useFilterModalStore((state) => state.isActive);
  const selectedFilters = useFilterStore((state) => state.selectedFilters);
  const prevDataRef = useRef(null);
  const location = useLocation(); // location.search → '?label=1&milestone=2&page=1'
  const applyQueryParams = useApplyQueryParams();

  useEffect(() => {
    if (!location.search || location.search === '?') {
      // location.search가 비어있다면 디폴트 필터 적용
      applyQueryParams(selectedFilters); // selectedFilters 초기값이 디폴트 필터임
    }
    fetchData(`${TEST_ISSUES_URL}${location.search}`);
  }, [location.search]);
  useEffect(() => {
    if (!response?.data) return;
    // const fetchedData = response.data;
    const { issues, users, labels, milestones, metaData } = response.data;
    const currentData = issues;
    const prevData = prevDataRef.current;

    // 객체 내용이 진짜로 바뀐 경우에만 실행
    const hasChanged = JSON.stringify(currentData) !== JSON.stringify(prevData);

    if (!hasChanged) return;
    prevDataRef.current = currentData; // 현재 값을 기억해둠

    setIssues(currentData); // ✅ 상태 변화 감지해서 후처리
    setMetaData(metaData);
    setFilterData(users, labels, milestones);
  }, [response?.data]);

  return (
    <Container>
      <MainPageHeaderTap />
      <Kanban>
        <KanbanHeader>
          <HeaderLeft>
            <CheckBox isDisabled={true} />
            <IsOpenFilter />
          </HeaderLeft>
          <HeaderRight>
            <DetailFilterTriigerButton />
          </HeaderRight>
        </KanbanHeader>
        <KanbanMain />
      </Kanban>
      {isActive && <DetailFilterModal />}
    </Container>
  );
}
