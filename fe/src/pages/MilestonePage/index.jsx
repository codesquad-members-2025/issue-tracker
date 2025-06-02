import {
  MilestoneInfo,
  MilestoneController,
  ProgressIndicator,
  OpenMilestoneButton,
  CloseMilestoneButton,
} from '@/base-ui/milestoneDetail';
import styled from 'styled-components';
import MilestoneLabelHeader from '@/units/common/MilestoneLabelHeader';
import useDataFetch from '@/hooks/useDataFetch';
import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { GET_MILESTONE } from '@/api/milestones';
import getOptionWithToken from '@/utils/getOptionWithToken/getOptionWithToken';
import useMilestoneStore from '@/stores/milestoneStore';
import MilestoneCreateForm from '@/base-ui/milestoneDetail/MilestoneCreateForm';
import { getPatchUrl, POST_MILESTONE } from '@/api/milestones';

export default function MilestonePage() {
  const { response, fetchData } = useDataFetch({ fetchType: 'Milestone' });
  const [isAddTableOpen, setIsAddTableOpen] = useState(false);
  const [searchParam, setSearchParam] = useSearchParams();
  const milestone = useMilestoneStore((s) => s.milestone);
  const initMilestones = useMilestoneStore((s) => s.initMilestones);
  const GEToptions = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  };

  function submitHandler({
    fetchMethod,
    name,
    milestoneId = null,
    description = null,
    endDate = null,
    isOpen = null,
  }) {
    const isPatch = fetchMethod === 'PATCH';
    const API = isPatch ? getPatchUrl(milestoneId) : POST_MILESTONE;
    const body = isPatch ? { name, description, endDate, isOpen } : { name, description, endDate };
    const fetchOption = {
      method: fetchMethod,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    };
    fetchData(API, getOptionWithToken(fetchOption));
    setIsAddTableOpen(false);
  }

  function statusHandler({ isOpen, milestoneId }) {
    const API = getPatchUrl(milestoneId);
    const fetchOption = {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ isOpen: !isOpen }),
    };
    fetchData(API, getOptionWithToken(fetchOption));
  }
  useEffect(() => {
    if (!searchParam.has('isOpen')) {
      setSearchParam({ isOpen: 'true' }); // selectedFilters 초기값이 디폴트 필터임
    }
    //항상 현재의 쿼리파람을 기준으로 GET 요청
    // 현재의 마일스톤 페이지에서 마일스톤 조작 액션이 일어나면 항상 GET 요청으로 새로운 데이터를 이 스코프에서 받아온다.
    fetchData(`${GET_MILESTONE}?${searchParam.toString()}`, getOptionWithToken(GEToptions));
  }, [searchParam]);

  useEffect(() => {
    //데이터 GET 으로 받아오면 스토어 초기화 하는 로직
    if (!response?.data) return;
    initMilestones(response.data);
  }, [response]);

  return (
    <Container>
      <MilestoneLabelHeader
        isLabel={false}
        isValid={!isAddTableOpen}
        addHandler={() => setIsAddTableOpen(true)}
      />
      {isAddTableOpen && (
        <MilestoneCreateForm
          isAdd={true}
          onCancel={() => setIsAddTableOpen(false)}
          onSubmit={(data) => submitHandler({ ...data, fetchMethod: 'POST' })}
        />
      )}
      <Kanban>
        <KanbanHeader>
          <HeaderLeft>
            <OpenMilestoneButton
              isOpen={searchParam.get('isOpen') === 'true'}
              number={milestone.openCount}
              onClick={() => setSearchParam({ isOpen: 'true' })}
            />
            <CloseMilestoneButton
              isOpen={searchParam.get('isOpen') === 'true'}
              number={milestone.closedCount}
              onClick={() => setSearchParam({ isOpen: 'false' })}
            />
          </HeaderLeft>
        </KanbanHeader>
        {milestone.milestones.map((m) => {
          return (
            <Item key={m.milestoneId}>
              <MilestoneInfo
                milestoneName={m.name}
                endDate={m.endDate}
                description={m.description}
              />
              <ItemRightWrapper>
                <MilestoneController isOpen={m.isOpen} statusHandler={statusHandler} />
                <ProgressIndicator
                  processingRate={m.processingRate}
                  openIssueCount={m.openIssue}
                  closeIssueCount={m.closeIssue}
                />
              </ItemRightWrapper>
            </Item>
          );
        })}
      </Kanban>
    </Container>
  );
}

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
`;

const KanbanHeader = styled.div`
  padding: 16px 32px;
  width: 100%;
  height: 64px;
  display: flex;
  align-items: center;
  background-color: ${({ theme }) => theme.surface.bold};
  border-bottom: 1px solid ${({ theme }) => theme.border.default};
  border-top-left-radius: 16px;
  border-top-right-radius: 16px;
`;

const HeaderLeft = styled.div`
  display: flex;
  gap: 24px;
  align-items: center;
`;

const Item = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 24px 32px;
`;
const ItemRightWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  align-items: flex-end;
`;
