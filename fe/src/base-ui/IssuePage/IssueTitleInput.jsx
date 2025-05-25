/*
* 해당 컴포넌트는 titleLabel,titleType, titleValue, changeHandler 를 prop 로 입력 받습니다.
* 컴포넌트 로직 흐름
-> 해당 컴포넌트가 사용될 페이지의 타입을 titleType로 넣어줍니다.
-> 사용자가 input 태그에 입력시 해당 입력 값을 상태로 관리합니다.
-> onChange 이벤트가 발생하면 상위 컴포넌트의 상태를 변경하고 변경된 value 를 prop으로 입력받아 사용자에게 value 로 보여줍니다.
*/

import styled from 'styled-components';
import { typography } from '@/styles/foundation';

const Container = styled.div`
  display: flex;
  flex-direction: ${({ $type }) => ($type === 'new' ? 'column' : 'row')};
  align-items: flex-start;
  padding: 8px 16px;
  width: 100%;
  height: ${({ $type }) => ($type === 'new' ? '56px' : '40px')};
  background-color: ${({ $isEmpty, theme }) =>
    $isEmpty ? theme.surface.bold : theme.surface.strong};
  border-radius: 16px;
`;

const TitleGuideLabel = styled.div`
  ${typography.display.medium12};
  color: ${({ theme }) => theme.text.weak};
  width: 64px;
`;

const TitleInput = styled.input`
  ${typography.display.medium16}
  color: ${({ theme }) => theme.text.default};
  padding: 16px;
`;
// 상위에서 prop을 받아서 value를 보여준다.
export function IssueTitleInput({ titleLabel, titleType, titleValue, changeHandler }) {
  const isEmpty = !titleValue;
  return (
    <Container $isEmpty={isEmpty} $type={titleType}>
      {titleValue && <TitleGuideLabel>{titleLabel}</TitleGuideLabel>}
      <TitleInput placeholder="제목" value={titleValue} onChange={changeHandler} />
    </Container>
  );
}
