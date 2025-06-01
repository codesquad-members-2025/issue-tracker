import styled from '@emotion/styled';

interface LabelAboveProps {
  isFloating: boolean;
  children: React.ReactNode;
}

export default function LabelAbove({ isFloating, children }: LabelAboveProps) {
  return <StyledLabel isFloating={isFloating}>{children}</StyledLabel>;
}

const StyledLabel = styled.label<{ isFloating: boolean }>`
  position: absolute;
  left: 16px;
  top: 8px;

  transform: ${({ isFloating }) =>
    isFloating ? 'translateY(0) scale(0.75)' : 'translateY(8px) scale(1)'};
  transform-origin: top left;

  transition: transform 0.2s ease;
  will-change: transform;

  ${({ theme }) => theme.typography.displayMedium16};
  color: ${({ theme }) => theme.neutral.text.weak};
  pointer-events: none;
`;
