import { useParams } from 'react-router-dom';
import { useState } from 'react';
import styled from '@emotion/styled';
import useCreateComment from '../../hooks/useCreateComment';
import CommentInputSection from '@/features/newIssue/components/CommentInputSection';
import NewCommentActionButton from './NewCommentActionButton';

export default function CommentEditor() {
  const { id } = useParams();
  const [comment, setComment] = useState<string>('');

  const issueId = Number(id);

  const { mutate: createComment, isPending } = useCreateComment(issueId);

  const handleCreateComment = () => {
    if (!comment.trim()) return;
    createComment(comment, {
      onSuccess: () => {
        setComment('');
      },
      onError: err => {
        console.error(err.message);
        // TODO 에러처리
      },
    });
  };

  return (
    <NewCommentWrapper>
      <CommentInputSection
        value={comment}
        onChange={value => setComment(value)}
      />

      <NewCommentActionButton
        isSubmitDisabled={comment.trim() === '' || isPending}
        onSubmit={handleCreateComment}
      />
    </NewCommentWrapper>
  );
}

const NewCommentWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  gap: 32px;
  height: 248px;
`;
