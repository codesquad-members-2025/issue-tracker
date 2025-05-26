import styled from 'styled-components';
import NewIssueTile from '@/base-ui/IssuePage/NewIssueTile';
import SideBar from '@/units/SideBar';
import UserAvatar from '@/base-ui/utils/UserBadge';
import useIssueDetailStore from '@/stores/IssueDetailStore';
import NewIssueInputForm from '@/units/issuePage/NewIssueInputForm';
import { useAuthStore } from '@/stores/authStore';

const Body = styled.form`
  display: flex;
  flex-direction: column;
  padding: 32px 80px 80px 80px;
  gap: 24px;
`;
const Main = styled.div`
  display: flex;
  gap: 24px;
  align-items: flex-start;
  border-top: 1px solid ${({ theme }) => theme.border.default};
  padding-top: 24px;
`;

export default function NewIssuePage() {
  const issue = useIssueDetailStore((s) => s.issue);
  const titleText = '새로운 이슈 작성';

  function submitHandler(e) {
    e.preventDefault();
    console.log('test');
  }

  return (
    <Body onSubmit={submitHandler}>
      <NewIssueTile title={titleText} />
      <Main>
        <NewIssueInputForm />
        <SideBar />
      </Main>
    </Body>
  );
}
