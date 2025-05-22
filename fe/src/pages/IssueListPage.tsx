import { useState } from 'react';
import styled from '@emotion/styled';
import { type IssueStatus } from '@/features/issue/types/issue';
import VerticalStack from '@/layouts/VerticalStack';
import IssueAdvancedFilter from '@/features/issue/components/list/IssueAdvancedFilter';
import IssueListContainer from '@/features/issue/components/list/IssueListContainer';
import LabelMilestoneTab from '@/shared/components/LabelMilestoneTab';
import CreateIssueButton from '@/features/issue/components/CreateIssueButton';

export default function IssueListPage() {
  const [selectedTab, setSelectedTab] = useState<IssueStatus>('open');
  const [queryOptions, setQueryOptions] = useState('is:issue is:open');

  const handleSearchChange = (value: string) => {
    setQueryOptions(value);
  };

  return (
    <VerticalStack>
      <FilterHeader>
        <IssueAdvancedFilter
          searchValue={queryOptions}
          onSearchChange={handleSearchChange}
        />
        <RightGroup>
          {/* TODO 기능 구현 시 하드코딩 제거 */}
          <LabelMilestoneTab milestoneCount={5} labelCount={3} />
          <CreateIssueButton />
        </RightGroup>
      </FilterHeader>

      <IssueListContainer
        onChangeTab={status => setSelectedTab(status)}
        selected={selectedTab}
      />
    </VerticalStack>
  );
}

const FilterHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const RightGroup = styled.div`
  display: flex;
  align-items: center;
  gap: 12px;
`;
