import styled from '@emotion/styled';
import ClosedIcon from '@/assets/icons/archive.svg?react';
import OpenIcon from '@/assets/icons/alertCircle.svg?react';
import IssueTabButton from '@/features/issue/components/list/IssueTabButton';

interface IssueTabFilterProps {
  openCount: number;
  closeCount: number;
  selected: 'open' | 'closed';
  onChangeTab: (status: 'open' | 'closed') => void;
}

export default function IssueTabFilter({
  openCount,
  closeCount,
  selected,
  onChangeTab,
}: IssueTabFilterProps) {
  return (
    <TabWrapper>
      <IssueTabButton
        icon={<OpenIcon />}
        label="열린 이슈"
        count={openCount}
        selected={selected === 'open'}
        onClick={() => onChangeTab('open')}
      />
      <IssueTabButton
        icon={<ClosedIcon />}
        label="닫힌 이슈"
        count={closeCount}
        selected={selected === 'closed'}
        onClick={() => onChangeTab('closed')}
      />
    </TabWrapper>
  );
}

const TabWrapper = styled.div`
  display: flex;
  gap: 24px;
`;
