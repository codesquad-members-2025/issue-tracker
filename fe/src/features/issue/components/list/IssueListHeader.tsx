import styled from '@emotion/styled';
import IssueTabFilter from './IssueTabFilter';
import ChevronDownIcon from '@/assets/icons/chevronDown.svg?react';

interface ListHeaderProps {
  openCount: number;
  closeCount: number;
  selected: 'open' | 'closed';
  onChangeTab: (status: 'open' | 'closed') => void;
}

export default function IssueListHeader({
  openCount,
  closeCount,
  selected,
  onChangeTab,
}: ListHeaderProps) {
  return (
    <HeaderWrapper>
      <LeftSection>
        <Checkbox type="checkbox" />
        {/* TODO 공통 체크박스로 변경 */}

        <IssueTabFilter
          openCount={openCount}
          closeCount={closeCount}
          selected={selected}
          onChangeTab={onChangeTab}
        />
      </LeftSection>

      <RightSection>
        <DropdownIndicator label="담당자" />
        <DropdownIndicator label="레이블" />
        <DropdownIndicator label="마일스톤" />
        <DropdownIndicator label="작성자" />
      </RightSection>
    </HeaderWrapper>
  );
}

const HeaderWrapper = styled.div`
  display: flex;
  align-items: center;
  padding: 16px 32px;

  border-bottom: 1px solid ${({ theme }) => theme.borderColor.default};
  background-color: ${({ theme }) => theme.surfaceColor.default};
  color: ${({ theme }) => theme.textColor.default};
  ${({ theme }) => theme.typography.availableMedium16};
`;

const LeftSection = styled.div`
  display: flex;
  align-items: center;
  flex: 1;
  gap: 32px;
`;

const RightSection = styled.div`
  display: flex;
  gap: 16px;
`;

const Checkbox = styled.input`
  display: flex;
  justify-content: center;
  width: 16px;
  height: 32px;
  cursor: pointer;
`;

//TODO 공통 컴포넌트로 분리
function DropdownIndicator({ label }: { label: string }) {
  return (
    <IndicatorWrapper>
      <IndicatorLabel>{label}</IndicatorLabel>
      <ChevronDownIcon width={16} height={16} />
    </IndicatorWrapper>
  );
}

const IndicatorWrapper = styled.div`
  display: inline-flex;
  align-items: center;
  justify-content: space-between;
  gap: 4px;
  min-width: 80px;

  cursor: pointer;
`;

const IndicatorLabel = styled.span`
  white-space: nowrap;
  ${({ theme }) => theme.typography.availableMedium16};
`;
