import styled from '@emotion/styled';

interface DividerProps {
  variant?: 'default' | 'bold';
}

interface DividerVariant {
  variant: 'default' | 'bold';
}

export default function Divider({ variant = 'default' }: DividerProps) {
  return <StyledDivider variant={variant} />;
}

const StyledDivider = styled.div<DividerVariant>`
  height: 1px;
  background-color: ${({ theme, variant }) =>
    variant === 'bold'
      ? theme.neutral.border.active
      : theme.neutral.border.default};
`;
