import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import MainTitle from '@/base-ui/main-layout/MainTitle';
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

export default function SignUpPage() {
  const navigate = useNavigate();
  const mainButtonLabel = '회원가입';
  const buttonLabelText = '로그인 화면으로';
  function onSubmit(event) {
    event.preventDefault();
    //서버 통신 로직
  }

  function moveToLogIn() {
    navigate('/login');
  }
  return (
    <Container>
      <MainTitle />
      <InputForm mainButtonLabel={mainButtonLabel} />
      <SubButton buttonLabelText={buttonLabelText} onClick={moveToLogIn} />
    </Container>
  );
}
