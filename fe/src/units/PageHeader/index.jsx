import styled from 'styled-components';
import UserAvatar from '@/base-ui/utils/UserAvatar';
import MainTitle from '@/base-ui/main-layout/MainTitle';

const Container = styled.div`
  display: flex;
  width: 100%;
  height: 94px;
  justify-content: space-between;
  align-items: center;
  padding-left: 80px;
  padding-right: 80px;
`;

export default function PageHeader() {
  return (
    <Container>
      <MainTitle />
      <UserAvatar
        avatarUrl={
          'https://i.namu.wiki/i/Gdh52DwszzGUrmQmGkVxdh3HcJB9bSY3xKjhexBp6aWgR7GumtNvGdRNoSqAIba9MUuuwYFBIlRUVP4M6a9HYg.webp'
        }
      />
    </Container>
  );
}
