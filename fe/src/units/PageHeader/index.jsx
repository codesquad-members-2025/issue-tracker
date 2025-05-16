import styled from 'styled-components';
import UserAvatar from '@/base-ui/utils/UserAvatar';
import MainTitle from '@/base-ui/main-layout/MainTitle';
import ToggleTheme from '@/base-ui/utils/ToggleTheme';

const Container = styled.div`
  display: flex;
  width: 100%;
  height: 94px;
  justify-content: space-between;
  align-items: center;
  padding-left: 80px;
  padding-right: 80px;
`;

const Wrapper = styled.div`
  display: flex;
  gap: 10px;
  align-items: center;
`;

export default function PageHeader() {
  return (
    <Container>
      <MainTitle />
      <Wrapper>
        <ToggleTheme />
        <UserAvatar
          avatarUrl={
            'https://i.namu.wiki/i/Gdh52DwszzGUrmQmGkVxdh3HcJB9bSY3xKjhexBp6aWgR7GumtNvGdRNoSqAIba9MUuuwYFBIlRUVP4M6a9HYg.webp'
          }
        />
      </Wrapper>
    </Container>
  );
}
