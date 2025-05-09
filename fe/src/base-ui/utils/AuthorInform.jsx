import styled from 'styled-components';
import { typography } from '@/styles/foundation';

const Container = styled.div`
  ${typography.display.medium16};
  color: ${({ theme }) => theme.text.weak};
`;

export default function Authorform({ timeStamp, author, isOpen }) {
  const isOpenText = isOpen ? '열렸습니다' : '닫혔습니다';
  const text = `이 이슈가 ${timeStamp} 전에 ${author}님에 의해 ${isOpenText}`;
  return <Container>{text}</Container>;
}
