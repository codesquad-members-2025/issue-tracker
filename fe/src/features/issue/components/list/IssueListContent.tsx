import styled from '@emotion/styled';
import { type Issue } from '../../types/issue';
import IssueItem from './issueItem';

interface ListContentProps {
  issues: Issue[];
}

const EMPTY_ISSUE_TEXT = '표시할 이슈가 없습니다';

export default function IssueListContent({ issues }: ListContentProps) {
  return (
    <ContentWrapper>
      {issues.length === 0 ? (
        <EmptyState>{EMPTY_ISSUE_TEXT}</EmptyState>
      ) : (
        issues.map(issue => <IssueItem key={issue.id} {...issue} />)
      )}
    </ContentWrapper>
  );
}

const ContentWrapper = styled.ul`
  display: flex;
  flex-direction: column;
`;

const EmptyState = styled.li`
  padding: 40px 0;
  text-align: center;
  color: ${({ theme }) => theme.textColor.weak};
  ${({ theme }) => theme.typography.availableMedium16};
`;
