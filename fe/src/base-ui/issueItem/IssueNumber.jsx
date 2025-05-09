/*
입력: 이슈 고유 번호
출력: 입력받은 이슈 번호 렌더링
*/
import styled from 'styled-components';
import { typography } from '@/styles/foundation';

const Container = styled.div`
  ${typography.display.medium16}
  color:${({ theme }) => theme.text.weak};
`;

export default function IssueNumber({ issueNumber }) {
  const issueLabel = '#';
  return (
    <Container>
      {issueLabel}
      {issueNumber}
    </Container>
  );
}
