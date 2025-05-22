import styled from '@emotion/styled';

const VerticalStack = styled.div<{ gap?: number; padding?: number }>`
  display: flex;
  flex-direction: column;
  gap: ${({ gap = 24 }) => gap}px;
`;

export default VerticalStack;
