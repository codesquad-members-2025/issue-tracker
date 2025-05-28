/** @jsxImportSource @emotion/react */
import styled from '@emotion/styled';
import DropdownIcon from '@/assets/icons/chevronDown.svg?react';

interface DropdownIndicatorProps {
  label: string;
  disabled?: boolean;
}

function DropdownIndicator({
  label,
  disabled = false,
}: DropdownIndicatorProps) {
  return (
    <IndicatorWrapper type="button" disabled={disabled}>
      <IndicatorLabel>{label}</IndicatorLabel>
      <DropdownIcon width={16} height={16} />
    </IndicatorWrapper>
  );
}

export default DropdownIndicator;

const IndicatorWrapper = styled.button`
  all: unset;
  display: inline-flex;
  align-items: center;
  justify-content: space-between;
  gap: 4px;
  min-width: 80px;

  cursor: pointer;
  color: ${({ theme }) => theme.neutral.text.default};

  &:hover {
    opacity: ${({ theme }) => theme.opacity.hover};
  }

  &:disabled {
    opacity: ${({ theme }) => theme.opacity.disabled};
    cursor: not-allowed;
  }
`;

const IndicatorLabel = styled.span`
  white-space: nowrap;
  ${({ theme }) => theme.typography.availableMedium16};
`;
