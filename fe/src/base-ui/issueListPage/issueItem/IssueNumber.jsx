/*
입력: 이슈 고유 번호
출력: 입력받은 이슈 번호 렌더링
*/
import styled from 'styled-components';
import { typography } from '@/styles/foundation';

const BaseNumber = styled.div`
  color: ${({ theme }) => theme.text.weak};
`;

const SmallNumber = styled(BaseNumber)`
  ${typography.display.medium16}
`;

const LargeNumber = styled(BaseNumber)`
  ${typography.display.bold32}
`;

export function IssueNumber({ issueNumber }) {
  const issueLabel = '#';
  return (
    <SmallNumber>
      {issueLabel}
      {issueNumber}
    </SmallNumber>
  );
}
export function LargeIssueNumber({ issueNumber }) {
  const issueLabel = '#';
  return (
    <LargeNumber>
      {issueLabel}
      {issueNumber}
    </LargeNumber>
  );
}
