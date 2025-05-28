import styled from 'styled-components';
import { useState } from 'react';
import CommentHeader from './CommentHeader';
import { typography } from '@/styles/foundation';
import CommentInput from '@/base-ui/IssuePage/CommentInput';
import { CompleteEditBtn, CancelEditBtn } from '@/base-ui/IssuePage/IssueControllButtons';

const CommentContainer = styled.div`
  display: flex;
  flex-direction: column;
  border: 1px solid ${({ theme }) => theme.border.default};
  border-radius: 16px;
`;

const CommentDisplayArea = styled.div`
  ${typography.display.medium16}
  background-color:${({ theme }) => theme.surface.strong};
  white-space: pre-line;
  padding: 16px 24px;
`;
const EditModeWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
  align-items: flex-end;
`;
const ButtonWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
`;

export default function DisplayComment({ commentContent }) {
  const [isEdit, setIsEdit] = useState(false);

  function editTriggerHandler() {
    //디테일 이슈 스토어의 comments에서 comment 로 상태 초기화. -> 편집모드에서 해당 상태를 구독해서 보여줘야한다.
    setIsEdit(true);
  }

  const comment =
    '이슈 디테일 스토어의 comment 상태를 구독하고 있어야한다. -> 실시간으로 편집 되는 ui를 유저에게 실시간으로 렌더링 시켜서 보여줘야한다.';

  const setFile = '디테일 이슈의 코멘츠의 파일 상태를 변경시키는 액션구독';

  function cancleEditHandler() {
    // 다시 편집 모드 전으로 돌아가니까 작성중이던 내용도 사라지고 commentContent를 보여준다.
    setIsEdit(false);
  }

  return (
    <EditModeWrapper>
      <CommentContainer>
        {isEdit ? (
          <CommentInput
            commentLabel="코멘트를 입력하세요."
            commentType="detail"
            commentValue={comment}
            changeHandler={(e) =>
              '디테일 이슈 스토어의 comment 상태를 변경시키는 commentChangeHandler를 적용시켜야한다.'
            }
            setFile={setFile}
            files={'디테일 이슈의 comments의 파일 상태를 구독해야한다'}
            headerComponent={<CommentHeader onClick={editTriggerHandler} />}
          />
        ) : (
          [
            <CommentHeader onClick={editTriggerHandler} />,
            <CommentDisplayArea>{commentContent}</CommentDisplayArea>,
          ]
        )}
      </CommentContainer>
      {isEdit && (
        <ButtonWrapper>
          <CancelEditBtn onClick={cancleEditHandler} />
          <CompleteEditBtn
            onClick={'등록 핸들러(PATCH 요청)+ 편집모드 false 로 변경.'}
            isValid={'유효성 검사 훅 사용'}
          />
        </ButtonWrapper>
      )}
    </EditModeWrapper>
  );
}
