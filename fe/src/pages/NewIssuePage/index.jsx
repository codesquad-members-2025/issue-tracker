import styled from 'styled-components';
import NewIssueTile from '@/base-ui/IssuePage/NewIssueTile';
import SideBar from '@/units/SideBar';
import UserAvatar from '@/base-ui/utils/UserBadge';
import { IssueTitleInput } from '@/base-ui/IssuePage/NewIssueInput';

const Body = styled.div`
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

const InputWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

export default function NewIssuePage() {
  const titleText = '새로운 이슈 작성';
  return (
    <Body>
      <NewIssueTile title={titleText} />
      <Main>
        <SideBar />
        <IssueTitleInput />
      </Main>
    </Body>
  );
}
