import styled from 'styled-components';
import { NavLink } from 'react-router-dom';

const SubButton = styled.button`
  ${typography.available.medium16};
  width: 320px;
  height: 40px;
  background-color: ${({ theme }) => theme.surface.default};
  color: ${({ theme }) => theme.text.default};
  border: none;
  border-radius: 16px;
  cursor: pointer;
  &:hover {
    background-color: ${({ theme }) => theme.surface.bold};
  }
`;
export default function LoginPage() {
  function onSubmit(event) {
    event.preventDefault();
    const validationError = validateUsername(Id);
    if (validationError) {
      setError(validationError);
      return;
    }
    setError('');
  }
  return;
}

/*
유틸에서 인풋 컴포넌트 사용할때 상황에 맞게 회원가입 navlink 컴포넌트 사용필요

<SubButton NavLink="/signUp">회원가입</SubButton>;
*/
