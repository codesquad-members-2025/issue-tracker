/** @jsxImportSource @emotion/react */
import styled from '@emotion/styled';
import SelectedIcon from '@/assets/icons/checkOnCircle.svg?react';
import UnselectedIcon from '@/assets/icons/checkOffCircle.svg?react';

interface BaseOption {
  id: number;
  name: string;
  selected: boolean;
}

interface DropdownPanelProps<
  T extends Record<string, unknown> = Record<string, unknown>,
> {
  options: (BaseOption & T)[];
  onSelect: (id: number) => void;
  renderOption?: (option: BaseOption & T) => React.ReactNode;
}

export default function DropdownPanel<
  T extends Record<string, unknown> = Record<string, unknown>,
>({ options, onSelect, renderOption }: DropdownPanelProps<T>) {
  return (
    <>
      {options.map(option => (
        <OptionButton
          key={option.id}
          onClick={() => onSelect(option.id)}
          isSelected={option.selected}
        >
          {renderOption ? renderOption(option) : <span>{option.name}</span>}
          {option.selected ? <SelectedIcon /> : <UnselectedIcon />}
        </OptionButton>
      ))}
    </>
  );
}

const OptionButton = styled.button<{ isSelected: boolean }>`
  height: 44px;
  padding: 0 16px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font: ${({ theme, isSelected }) =>
    isSelected
      ? theme.typography.displayBold16
      : theme.typography.availableMedium16};
  color: ${({ theme, isSelected }) =>
    isSelected ? theme.neutral.text.default : theme.neutral.text.weak};
  background: transparent;
  border: none;
  border-bottom: 1px solid ${({ theme }) => theme.neutral.border.default};
  cursor: pointer;

  &:last-of-type {
    border-bottom: none;
  }
`;
