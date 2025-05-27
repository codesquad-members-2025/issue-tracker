import styled from 'styled-components';
import NewIssueTile from '@/base-ui/IssuePage/NewIssueTile';
import SideBar from '@/units/SideBar';
import UserAvatar from '@/base-ui/utils/UserBadge';
import useIssueDetailStore from '@/stores/IssueDetailStore';
import NewIssueInputForm from '@/units/issuePage/NewIssueInputForm';
import { useAuthStore } from '@/stores/authStore';
import NewIssueFooter from '@/units/issuePage/NewIssueFooter';
import useValidation from '@/hooks/useValidation';

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
  const titleText = '새로운 이슈 작성';
  const existTitle = useIssueDetailStore.getState().issue.title; //기존의 초기 title

  //🐛 -> 리렌더링 될때마다 계속해서 커스텀 훅의 초깃값이 변함. -> 유효성 검사 불가.
  const { isValid, setCurrentInput, currentInput } = useValidation({
    existedString: '',
  });

  function submitHandler(e) {
    e.preventDefault();
    console.log('test');
  }

  return (
    <Body onSubmit={submitHandler}>
      <NewIssueTile title={titleText} />
      <Main>
        <NewIssueInputForm setCurrentInput={setCurrentInput} />
        <SideBar />
      </Main>
      <NewIssueFooter isValid={isValid} />
    </Body>
  );
}
