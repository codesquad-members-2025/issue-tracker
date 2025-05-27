import styled from 'styled-components';
import { IssueTitleInput } from '@/base-ui/IssuePage/IssueTitleInput';
import CommentInput from '@/base-ui/IssuePage/CommentInput';
import useIssueDetailStore from '@/stores/IssueDetailStore';
import { shallow } from 'zustand/shallow';
import useValidation from '@/hooks/useValidation';

const InputWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  width: 912px;
`;

export default function NewIssueInputForm({ setCurrentInput }) {
  const issue = useIssueDetailStore((s) => s.issue);
  const comment = useIssueDetailStore((s) => s.comment.content);
  const titleChangeHandler = useIssueDetailStore((s) => s.changeTitle);
  const commentChangeHandler = useIssueDetailStore((s) => s.addOrEditComment);
  const setFiles = useIssueDetailStore((s) => s.setFiles);

  // const { titleChangeHandler, commentChangeHandler } = useIssueDetailStore(
  //   (s) => ({
  //     titleChangeHandler: s.changeTitle,
  //     commentChangeHandler: s.changeComment,
  //   }),
  //   shallow,
  // );
  // -> 왜 shallow 를 사용해도 무한 루프 에러가 발생할까?

  return (
    <InputWrapper>
      <IssueTitleInput
        titleLabel="제목"
        titleType="new"
        titleValue={issue.title}
        changeHandler={(e) => {
          titleChangeHandler(e.target.value);
          setCurrentInput(e.target.value);
        }}
      />
      <CommentInput
        commentLabel="코멘트를 입력하세요."
        commentType="new"
        commentValue={comment}
        changeHandler={(e) => commentChangeHandler(e.target.value)}
        setFiles={setFiles}
        files={issue.files}
      />
    </InputWrapper>
  );
}
