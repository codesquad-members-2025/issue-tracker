import styled from 'styled-components';
import UserAvatar from '@/base-ui/utils/UserAvatar';
import MainTitle from '@/base-ui/main-layout/MainTitle';
import ToggleTheme from '@/base-ui/utils/ToggleTheme';
import { useAuthStore } from '@/stores/authStore';
import { useNavigate } from 'react-router-dom';

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

const LogOut = styled.button`
  cursor: pointer;
  svg {
    color: ${({ theme }) => theme.text.default};
  }
`;

export default function PageHeader() {
  const resetUser = useAuthStore((state) => state.resetUser);
  const profileImageUrl = useAuthStore((s) => s.profileImageUrl);
  const navigate = useNavigate();

  function logoutHandler(resetUser) {
    localStorage.removeItem('token');
    localStorage.removeItem('auth-store');
    resetUser();
    navigate('/login');
  }
  return (
    <Container>
      <MainTitle />
      <Wrapper>
        <ToggleTheme />
        <LogOut onClick={() => logoutHandler(resetUser)}>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            height="30px"
            viewBox="0 -960 960 960"
            width="30px"
            fill="currentcolor"
          >
            <path d="M200-120q-33 0-56.5-23.5T120-200v-560q0-33 23.5-56.5T200-840h280v80H200v560h280v80H200Zm440-160-55-58 102-102H360v-80h327L585-622l55-58 200 200-200 200Z" />
          </svg>
        </LogOut>
        <UserAvatar avatarUrl={profileImageUrl} />
      </Wrapper>
    </Container>
  );
}
