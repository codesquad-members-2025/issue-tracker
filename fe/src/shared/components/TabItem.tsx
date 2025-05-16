import styled from '@emotion/styled';
import { type ReactNode } from 'react';

interface TabItemProps {
  icon: ReactNode;
  label: string;
  count: number;
  onClick?: () => void;
  isActive?: boolean;
}
export default function TabItem({
  icon,
  label,
  count,
  onClick,
  isActive = false,
}: TabItemProps) {
  return (
    <TabItemWrapper onClick={onClick} isActive={isActive}>
      {icon}
      <TabLabel isActive={isActive}>{`${label}(${count})`}</TabLabel>
    </TabItemWrapper>
  );
}

const TabItemWrapper = styled.button<{ isActive: boolean }>`
  width: 160px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  padding: 8px 0;

  cursor: pointer;

  background-color: ${({ isActive, theme }) =>
    isActive ? theme.surfaceColor.bold : theme.surfaceColor.default};
  color: ${({ isActive, theme }) =>
    isActive ? theme.textColor.strong : theme.textColor.default};

  &:hover {
    opacity: ${({ theme }) => theme.opacity.hover};
  }
`;

const TabLabel = styled.span<{ isActive: boolean }>`
  ${({ isActive, theme }) =>
    isActive
      ? theme.typography.selectedBold16
      : theme.typography.availableMedium16};
`;
