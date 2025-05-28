import { useState } from 'react';
import styled from 'styled-components';
import { typography } from '@/styles/foundation';
import { LargeIssueTitle } from '@/base-ui/utils/IssueTitle';
import { LargeIssueNumber } from '@/base-ui/issueListPage/issueItem';
import {
  EditTitleBtn,
  CancelEditBtn,
  CloseIssueBtn,
  CompleteEditBtn,
  OpenIssueBtn,
} from '@/base-ui/IssuePage/IssueControllButtons';
import IssueStatusLabel from '@/base-ui/IssuePage/IssueStatusLabel';
import AuthorInform from '@/base-ui/utils/AuthorInform';
import useIssueDetailStore from '@/stores/IssueDetailStore';

const Container = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 16px;
  align-items: flex-start;
  padding-bottom: 24px;
  border-bottom: 1px solid ${({ theme }) => theme.border.default};
`;

const TitleAndButtons = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Title = styled.div`
  display: flex;
  gap: 8px;
  align-items: center;
`;

const Buttons = styled.div`
  display: flex;
  gap: 16px;
`;

const StatesInfo = styled.div`
  display: flex;
  gap: 8px;
  align-items: center;
`;

const CommentsCount = styled.div`
  ${typography.display.medium16}
  color:${({ theme }) => theme.text.weak};
  margin-left: 24px;
`;

export default function DetailIssueHeader() {
  const [isEdit, setIsEdit] = useState(false);
  const issue = useIssueDetailStore((s) => s.issue);

  function getLeftBtn() {
    if (isEdit) return <CancelEditBtn onClick={'타이틀 편집 취소 핸들러'} />;
    return <EditTitleBtn onClick={'타이들 편집 트리거 핸들러'} />;
  }
  function getRightBtn() {
    if (isEdit)
      return (
        <CompleteEditBtn
          onClick={'타이틀 편집 등록 핸들러'}
          isValid={'유효성 관리 커스텀 훅의 반환 상태'}
        />
      );

    return issue.isOpen ? (
      <CloseIssueBtn onClick={'이슈 닫기 핸들러'} />
    ) : (
      <OpenIssueBtn onClick={'이슈 열기 핸들러'} />
    );
  }

  return (
    <Container>
      <TitleAndButtons>
        <Title>
          <LargeIssueTitle title={'스토어에서 이슈 타이틀 구독해서 받아오기'} />
          <LargeIssueNumber issueNumber={'스토어에서 이슈 번호 받아서 렌더링'} />
        </Title>
        <Buttons>{[getRightBtn(), getRightBtn()]}</Buttons>
      </TitleAndButtons>
      <StatesInfo>
        <IssueStatusLabel isOpen={'이슈의 열림 닫힘 여부'} />
        <AuthorInform lastModifiedAt={'이슈 편집시간'} author={'이슈 작성자'} />
        <CommentsCount>{'하나의 이슈에 달린 코멘트 갯수'}</CommentsCount>
      </StatesInfo>
    </Container>
  );
}
