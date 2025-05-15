/** @jsxImportSource @emotion/react */
// 레이블+마일스톤 필터 컴포넌트
import styled from "@emotion/styled";

const Group = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5rem; /* 8px */
`;

const Dropdown = styled.div`
  display: flex;
  align-items: center;
  padding: 0.5rem; /* 8px */
  font-size: 1rem;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colors.border.default}`};
  border-radius: ${({ theme }) => theme.radius.medium};
  background-color: ${({ theme }) => theme.colors.surface.strong};
  cursor: pointer;
  transition: opacity 0.2s;
  &:hover {
    opacity: ${({ theme }) => theme.opacity.hover};
  }
`;

interface FilterDropdownProps {
  label: string;
  onClick: () => void;
}

export const FilterGroup: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => <Group>{children}</Group>;

export const FilterDropdown: React.FC<FilterDropdownProps> = ({
  label,
  onClick,
}) => <Dropdown onClick={onClick}>{label}</Dropdown>;
