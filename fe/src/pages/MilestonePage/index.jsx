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
import { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { GET_MILESTONE } from '@/api/milestones';
import getOptionWithToken from '@/utils/getOptionWithToken/getOptionWithToken';
import useMilestoneStore from '@/stores/milestoneStore';

export default function MilestonePage() {
  const { response, fetchData } = useDataFetch({ fetchType: 'Milestone' });
  const [searchParam, setSearchParam] = useSearchParams();
  const milestone = useMilestoneStore((s) => s.milestone);
  const initMilestones = useMilestoneStore((s) => s.initMilestones);
  const GEToptions = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  };

  useEffect(() => {
    if (!searchParam.has('isOpen')) {
      setSearchParam({ isOpen: 'true' }); // selectedFilters 초기값이 디폴트 필터임
    }
    fetchData(GET_MILESTONE, getOptionWithToken(GEToptions));
  }, [searchParam]);

  useEffect(() => {
    if (!response?.data) return;
    initMilestones(response.data);
  }, [response]);
  return (
    <Container>
      <MilestoneLabelHeader isLabel={false} isValid={s} addHandler={s} />;
      <Kanban>
        <KanbanHeader>
          <HeaderLeft>
            <OpenMilestoneButton />
            <CloseMilestoneButton />
          </HeaderLeft>
        </KanbanHeader>
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
