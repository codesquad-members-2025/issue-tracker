/*
- 입력 : 체크박스의 활성화, 비활성화 여부를 입력 받는다.
- 출력: 입력 값에 따라 활성화(= 파란 체크박스), 비활성화 (= 하얀 체크박스) svg를 렌더링한다.
*/
import styled from 'styled-components';
import { getBorder } from '@/styles/foundation/border';

const getIconBorder = (props) => getBorder(props.theme.border.default).default;

const Container = styled.button`
  border: ${({ $isActive }) => ($isActive ? 'none' : getIconBorder)};
  display: flex;
  width: 16px;
  height: 16px;
  border-radius: 1.6px;
  align-items: center;
`;

function getCheckBox(isDisAble, isActive) {
  if (isDisAble) {
    return (
      <svg
        width="16"
        height="16"
        viewBox="0 0 16 16"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <rect x="0.5" y="0.5" width="15" height="15" rx="1.5" fill="#007AFF" stroke="#007AFF" />
        <path
          d="M6 8H10"
          stroke="#FEFEFE"
          strokeWidth="1.6"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
      </svg>
    );
  } else if (isActive) {
    return (
      <svg
        width="16"
        height="16"
        viewBox="0 0 16 16"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <rect x="0.5" y="0.5" width="15" height="15" rx="1.5" fill="#007AFF" stroke="#007AFF" />
        <path
          d="M10.6667 6L7 9.67333L5 7.67333"
          stroke="#FEFEFE"
          strokeWidth="1.6"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
      </svg>
    );
  } else {
    return null;
  }
}

export default function CheckBox({ isDisAble, isActive, onClick }) {
  return (
    <Container $isActive={isActive} onClick={onClick}>
      {isActive ? getCheckBox(isDisAble, isActive) : null}
    </Container>
  );
}
