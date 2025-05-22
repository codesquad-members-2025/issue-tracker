import styled from '@emotion/styled';
import ChevronDownIcon from '@/assets/icons/chevronDown.svg?react';
import SearchIcon from '@/assets/icons/search.svg?react';

interface IssueAdvancedFilterProps {
  searchValue: string;
  onSearchChange: (value: string) => void;
  placeholder?: string;
}

export default function IssueAdvancedFilter({
  searchValue,
  onSearchChange,
  placeholder = 'placeholder',
}: IssueAdvancedFilterProps) {
  return (
    <FilterWrapper>
      <FilterButton type="button">
        <DropdownIndicator label="필터" />
      </FilterButton>

      <SearchArea>
        <SearchIcon width={16} height={16} />
        <SearchInput
          value={searchValue}
          onChange={e => onSearchChange(e.target.value)}
          placeholder={placeholder}
        />
      </SearchArea>
    </FilterWrapper>
  );
}

const FilterWrapper = styled.div`
  width: 560px;
  display: flex;
  align-items: center;
  overflow: hidden;
  border-radius: ${({ theme }) => theme.radius.medium};
  border: 1px solid ${({ theme }) => theme.neutral.border.default};
`;

const FilterButton = styled.button`
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 4px 24px;
  border: none;
  background-color: ${({ theme }) => theme.neutral.surface.default};
  color: ${({ theme }) => theme.neutral.text.default};
  ${({ theme }) => theme.typography.availableMedium16};
  cursor: pointer;
`;

const SearchArea = styled.label`
  display: flex;
  align-items: center;
  flex: 1;
  gap: 8px;
  padding: 8px 24px;
  height: 100%;
  border-left: 1px solid ${({ theme }) => theme.neutral.border.default};
  background-color: ${({ theme }) => theme.neutral.surface.bold};
  color: ${({ theme }) => theme.neutral.text.default};
`;

const SearchInput = styled.input`
  all: unset;
  flex: 1;
  color: ${({ theme }) => theme.neutral.text.weak};
  ${({ theme }) => theme.typography.availableMedium16};
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
  width: 80px;
  display: inline-flex;
  align-items: center;
  justify-content: space-between;
  cursor: pointer;
`;

const IndicatorLabel = styled.span`
  padding: 4px 0;
  white-space: nowrap;
  ${({ theme }) => theme.typography.availableMedium16};
`;
