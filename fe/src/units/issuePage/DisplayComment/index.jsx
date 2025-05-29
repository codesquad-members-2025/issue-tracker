import styled from 'styled-components';
import { useState } from 'react';
import CommentHeader from './CommentHeader';
import CommentInput from '@/base-ui/IssuePage/CommentInput';
import { CompleteEditBtn, CancelEditBtn } from '@/base-ui/IssuePage/IssueControllButtons';
import useIssueDetailStore from '@/stores/IssueDetailStore';
import { shallow } from 'zustand/shallow';
import CommentDisplayArea from '@/base-ui/IssuePage/CommentDisplayArea';
import useValidation from '@/hooks/useValidation';

// 하단 부의 새로운 코멘트 입력은 제외한 컴포넌트 -> 새로운 코멘트는 다른 유닛 컴포넌트에서 다루기!
export default function DisplayComment({ commentObj, commentPatchHandler }) {
  const { commentId, content, issueFileUrl, authorNickname, lastModifiedAt, profileImageUrl } =
    commentObj;
  const [isEdit, setIsEdit] = useState(false);
  const startEditComment = useIssueDetailStore((s) => s.startEditComment);
  const updateEditComment = useIssueDetailStore((s) => s.updateEditComment);
  const { isValid, setCurrentInput } = useValidation({ existedString: content });

  function editTriggerHandler() {
    //디테일 이슈 스토어의 comments에서 comment 로 상태 초기화. -> 편집모드에서 해당 상태를 구독해서 보여줘야한다.
    startEditComment(commentId);
    setIsEdit(true);
  }

  function commentValueHandler(value) {
    updateEditComment(commentId, value);
    setCurrentInput(value);
  }

  //편집 모드일 경우 사용되는 코멘트의 value
  // '이슈 디테일 스토어의 comment 상태를 구독하고 있어야한다. -> 실시간으로 편집 되는 ui를 유저에게 실시간으로 렌더링 시켜서 보여줘야한다.'
  const { editingContent, editingIssueFileUrl } = useIssueDetailStore(
    (s) => ({
      editingContent: s.commentsEditing[commentId]?.content || '',
      editingIssueFileUrl: s.commentsEditing[commentId]?.issueFileUrl || null,
    }),
    shallow,
  );

  //   '디테일 이슈의 코멘트의 파일 상태를 변경시키는 액션구독'
  const setFile = useIssueDetailStore((s) => s.setFileForEditComment);
  function commentFileHandler(file) {
    setFile(commentId, file);
  }

  function cancleEditHandler() {
    setIsEdit(false);
    // 기존의 작성중인 스토어의 코멘트 상태는 초기화할 필요가 없다! -> 다시 편집 버튼을 누르면 덮어 씌워서 괜찮다.
  }

  function completeEditHandler() {}

  return (
    <EditModeWrapper>
      <CommentContainer>
        {isEdit ? (
          <CommentInput
            commentLabel="코멘트를 입력하세요."
            commentType="detail"
            commentValue={editingContent}
            changeHandler={(e) => commentValueHandler(e.target.value)}
            setFile={commentFileHandler}
            files={editingIssueFileUrl}
            headerComponent={
              <CommentHeader
                authorNickname={authorNickname}
                authorProfileUrl={profileImageUrl}
                editTriggerHandler={editTriggerHandler}
                lastModifiedAt={lastModifiedAt}
              />
            }
          />
        ) : (
          //편집모드가 아닌 그냥 보여주는 모드
          [
            <CommentHeader
              authorNickname={authorNickname}
              authorProfileUrl={profileImageUrl}
              editTriggerHandler={editTriggerHandler}
              lastModifiedAt={lastModifiedAt}
            />,
            <CommentDisplayArea content={content} issueFileUrl={issueFileUrl} />,
          ]
        )}
      </CommentContainer>
      {isEdit && (
        <ButtonWrapper>
          <CancelEditBtn onClick={cancleEditHandler} />
          <CompleteEditBtn
            onClick={'등록 핸들러(PATCH 요청)+ 편집모드 false 로 변경.'}
            isValid={isValid}
          />
        </ButtonWrapper>
      )}
    </EditModeWrapper>
  );
}

const CommentContainer = styled.div`
  display: flex;
  flex-direction: column;
  border: 1px solid ${({ theme }) => theme.border.default};
  border-radius: 16px;
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
