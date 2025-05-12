import styled from '@emotion/styled';
import { Link } from 'react-router-dom';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  text-align: center;
`;

const Title = styled.h1`
  font-size: 4rem;
  margin-bottom: 1rem;
`;

const Message = styled.p`
  font-size: 1.25rem;
  margin-bottom: 2rem;
`;

const StyledLink = styled(Link)`
  color: #007aff;
  text-decoration: underline;
`;

const NofoundPage = () => {
  return (
    <Container>
      <Title>404</Title>
      <Message>페이지를 찾을 수 없습니다.</Message>
      <StyledLink to="/">메인 페이지로 돌아가기</StyledLink>
    </Container>
  );
};

export default NofoundPage;
