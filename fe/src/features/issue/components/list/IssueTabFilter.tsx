import styled from '@emotion/styled';
import ClosedIcon from '@/assets/icons/archive.svg?react';
import OpenIcon from '@/assets/icons/alertCircle.svg?react';

interface TabItemProps {
  icon: React.ReactNode;
  label: string;
  active: boolean;
  onClick: () => void;
}

interface IssueTabFilterProps {
  openCount: number;
  closeCount: number;
  selected: 'open' | 'closed';
  onChangeTab: (status: 'open' | 'closed') => void;
}

function TabItem({ icon, label, active, onClick }: TabItemProps) {
  return (
    <TabButton active={active} onClick={onClick}>
      <IconWrapper>{icon}</IconWrapper>
      <Label>{label}</Label>
    </TabButton>
  );
}

export default function IssueTabFilter({
  openCount,
  closeCount,
  selected,
  onChangeTab,
}: IssueTabFilterProps) {
  return (
    <TabWrapper>
      <TabItem
        icon={<OpenIcon />}
        label={`열린 이슈 (${openCount})`}
        active={selected === 'open'}
        onClick={() => onChangeTab('open')}
      />
      <TabItem
        icon={<ClosedIcon />}
        label={`닫힌 이슈 (${closeCount})`}
        active={selected === 'closed'}
        onClick={() => onChangeTab('closed')}
      />
    </TabWrapper>
  );
}

const TabWrapper = styled.div`
  display: flex;
  gap: 24px;
`;

const TabButton = styled.button<{ active?: boolean }>`
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 4px 0;
  color: ${({ active, theme }) =>
    active ? theme.textColor.strong : theme.textColor.default};
  ${({ theme }) => theme.typography.selectedBold16};

  cursor: pointer;

  &:hover {
    opacity: 0.8;
  }
`;

const IconWrapper = styled.span`
  display: flex;
  align-items: center;
  color: inherit;

  svg {
    width: 16px;
    height: 16px;
  }
`;

const Label = styled.span`
  color: inherit;
`;
