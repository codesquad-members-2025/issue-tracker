import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import Logo from '@/base-ui/loginPage/Logo';
import GitHubLoginButton from '@/base-ui/loginPage/GitHubLoginButton';
import OrLabel from '@/base-ui/loginPage/OrLabel';
import InputForm from '@/utils/InputForm';
import SubButton from '@/base-ui/loginPage/SubButton';
import ToggleTheme from '@/base-ui/utils/ToggleTheme';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
`;

export default function LoginPage() {
  const navigate = useNavigate();
  const mainButtonLabel = '아이디로 로그인';
  const buttonLabelText = '회원가입';
  function onSubmit(event) {
    event.preventDefault();
    //서버 통신 로직
  }

  function moveToSignUp() {
    navigate('/signUp');
  }
  return (
    <Container>
      <ToggleTheme />
      <Logo />
      <GitHubLoginButton />
      <OrLabel />
      <InputForm mainButtonLabel={mainButtonLabel} />
      <SubButton buttonLabelText={buttonLabelText} onClick={moveToSignUp} />
    </Container>
  );
}

/*
유틸에서 인풋 컴포넌트 사용할때 상황에 맞게 회원가입 navlink 컴포넌트 사용필요

<SubButton NavLink="/signUp">회원가입</SubButton>;
*/
