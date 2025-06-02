import styled from '@emotion/styled';
import { type ReactNode } from 'react';

interface TabButtonProps {
  left: ReactNode;
  right: ReactNode;
}

export default function TabButton({ left, right }: TabButtonProps) {
  return (
    <Container>
      {left}
      <Divider />
      {right}
    </Container>
  );
}

const Container = styled.div`
  width: 320px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border: 1px solid ${({ theme }) => theme.neutral.border.default};
  border-radius: ${({ theme }) => theme.radius.medium};
  overflow: hidden;
`;

const Divider = styled.div`
  width: 1px;
  height: 40px;
  background-color: ${({ theme }) => theme.neutral.border.default};
`;
