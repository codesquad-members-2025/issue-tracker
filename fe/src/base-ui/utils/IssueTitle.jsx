import styled from 'styled-components';
import { typography } from '@/styles/foundation';

const BaseTitle = styled.div`
  color: ${({ theme }) => theme.text.strong};
  display: flex;
  align-items: center;
`;

const SmallTitle = styled(BaseTitle)`
  ${typography.available.medium20};
`;
const LargeTitle = styled(BaseTitle)`
  ${typography.display.bold32};
`;

export function IssueTitle({ title }) {
  return (
    <SmallTitle>
      <div>{title}</div>
    </SmallTitle>
  );
}
export function LargeIssueTitle({ title }) {
  return (
    <LargeTitle>
      <div>{title}</div>
    </LargeTitle>
  );
}
