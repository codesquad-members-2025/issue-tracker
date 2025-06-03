import styled from 'styled-components';
import CommentInput from '@/base-ui/IssuePage/CommentInput';
import useIssueDetailStore from '@/stores/IssueDetailStore';
import { SmallContainerButton } from '@/base-ui/components/ContainerButtons';
import useValidation from '@/hooks/useValidation';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 24px;
`;

export default function NewComment({}) {
  const newComment = useIssueDetailStore((s) => s.newComment);
  const setFileForNewComment = useIssueDetailStore((s) => s.setFileForNewComment);
  const commentChangeHandler = useIssueDetailStore((s) => s.addNewComment);
  const { isValid, setCurrentInput } = useValidation({ existedString: '' });

  return (
    <Container>
      <CommentInput
        commentLabel="코멘트를 입력하세요."
        commentType="detail"
        commentValue={newComment.content}
        changeHandler={(e) => {
          commentChangeHandler(e.target.value);
          setCurrentInput(e.target.value);
        }}
        setFile={setFileForNewComment}
        files={newComment.issueFileUrl}
      />
      <SmallContainerButton disabled={!isValid}>
        <span>코멘트 작성</span>
      </SmallContainerButton>
    </Container>
  );
}
