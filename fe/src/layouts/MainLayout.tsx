import { Outlet } from 'react-router-dom';
import styled from '@emotion/styled';
import Header from '@/shared/components/Header';

export default function MainLayout() {
  return (
    <Container>
      <Header />
      <Outlet />
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 32px;
  padding: 0 80px;
`;
