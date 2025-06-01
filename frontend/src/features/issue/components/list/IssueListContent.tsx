import styled from '@emotion/styled';
import { type Issue } from '../../types/issue';
import IssueItem from './issueItem';

interface ListContentProps {
  issues: Issue[];
  isFetching: boolean;
  isError: boolean;
}

const EMPTY_ISSUE_TEXT = '표시할 이슈가 없습니다';
const ERROR_TEXT = '문제가 발생했습니다. 잠시 후 다시 시도해주세요.';

export default function IssueListContent({
  issues,
  isFetching,
  isError,
}: ListContentProps) {
  const isEmpty = issues.length === 0;

  const renderContent = () => {
    if (isError) return <Message text={ERROR_TEXT} isError />;
    if (!isFetching && isEmpty) return <Message text={EMPTY_ISSUE_TEXT} />;
    return issues.map(issue => <IssueItem key={issue.id} {...issue} />);
  };

  return (
    <ContentWrapper isFetching={isFetching}>{renderContent()}</ContentWrapper>
  );
}

const ContentWrapper = styled.ul<{ isFetching?: boolean }>`
  display: flex;
  flex-direction: column;
  opacity: ${({ isFetching }) => (isFetching ? 0.5 : 1)};
  transition: opacity 0.5s ease-in-out;
  min-height: 96px;
`;

const Message = ({
  text,
  isError = false,
}: {
  text: string;
  isError?: boolean;
}) => <MessageItem isError={isError}>{text}</MessageItem>;

const MessageItem = styled.li<{ isError?: boolean }>`
  padding: 40px 0;
  text-align: center;
  color: ${({ isError, theme }) => (isError ? 'red' : theme.neutral.text.weak)};
  ${({ theme }) => theme.typography.availableMedium16};
`;
