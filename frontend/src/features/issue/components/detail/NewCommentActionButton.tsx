import styled from '@emotion/styled';
import Button from '@/shared/components/Button';
import PlusIcon from '@/assets/icons/plus.svg?react';

interface NewCommentActionButtonProps {
  isSubmitDisabled: boolean;
  onSubmit: () => void;
}

export default function NewCommentActionButton({
  isSubmitDisabled,
  onSubmit,
}: NewCommentActionButtonProps) {
  return (
    <ButtonGroup>
      <Button
        size="small"
        icon={<PlusIcon />}
        disabled={isSubmitDisabled}
        onClick={onSubmit}
      >
        코멘트 작성
      </Button>
    </ButtonGroup>
  );
}

const ButtonGroup = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 24px;
`;
