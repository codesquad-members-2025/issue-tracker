import styled from 'styled-components';
import { IssueBadge, IssueNumber } from '@/base-ui/issueListPage/issueItem';
import { OpenIssueButton, CloseIssueButton } from '@/base-ui/issueListPage/IssueListHeader';
import CheckBox from '@/base-ui/utils/CheckBox';
import IssueTitle from '@/base-ui/utils/IssueTitle';
import Label from '@/base-ui/utils/Label';
import MileStoneTitle from '@/base-ui/utils/MileStoneTitle';
import AuthorInform from '@/base-ui/utils/AuthorInform';
import UserAvatar from '@/base-ui/utils/UserBadge';

const Container = styled.div`
  display: flex;
  justify-content: space-between;
`;

const LeftWrapper = styled.div`
  display: flex;
  flex-direction: column;

  gap: 8px;
  padding: 16px 54px 16px 32px;
`;

export default function IssueItem() {
  return <Container></Container>;
}
