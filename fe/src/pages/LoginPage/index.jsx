import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import MainTitle from '@/base-ui/main-layout/MainTitle';
import GitHubLoginButton from '@/base-ui/loginPage/GitHubLoginButton';
import OrLabel from '@/base-ui/loginPage/OrLabel';
import InputForm from '@/utils/InputForm';
import SubButton from '@/base-ui/loginPage/SubButton';

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

  function signUpHandler() {
    navigate('/signUp');
  }
  return (
    <Container>
      <MainTitle />
      <GitHubLoginButton />
      <OrLabel />
      <InputForm mainButtonLabel={mainButtonLabel} />
      <SubButton buttonLabelText={buttonLabelText} onClick={signUpHandler} />
    </Container>
  );
}

/*
유틸에서 인풋 컴포넌트 사용할때 상황에 맞게 회원가입 navlink 컴포넌트 사용필요

<SubButton NavLink="/signUp">회원가입</SubButton>;
*/
