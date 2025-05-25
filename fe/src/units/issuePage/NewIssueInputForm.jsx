import styled from 'styled-components';
import { IssueTitleInput } from '@/base-ui/IssuePage/IssueTitleInput';
import CommentInput from '@/base-ui/IssuePage/CommentInput';
import useIssueDetailStore from '@/stores/IssueDetailStore';
import { shallow } from 'zustand/shallow';

const InputWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

export default function NewIssueInputForm() {
  const issue = useIssueDetailStore((s) => s.issue);

  // const titleChangeHandler = useIssueDetailStore((s) => s.changeTitle);
  // const commentChangeHandler = useIssueDetailStore((s) => s.changeComment);

  const { titleChangeHandler, commentChangeHandler } = useIssueDetailStore(
    (s) => ({
      titleChangeHandler: s.changeTitle,
      commentChangeHandler: s.changeComment,
    }),
    shallow,
  );

  return (
    <InputWrapper>
      <IssueTitleInput
        titleLabel="제목"
        titleType="new"
        titleValue={issue.title}
        changeHandler={(e) => titleChangeHandler(e.target.value)}
      />
      <CommentInput
        commentLabel="코멘트를 입력하세요."
        commentType="new"
        commentValue={issue.comment}
        changeHandler={(e) => commentChangeHandler(e.target.value)}
      />
    </InputWrapper>
  );
}
