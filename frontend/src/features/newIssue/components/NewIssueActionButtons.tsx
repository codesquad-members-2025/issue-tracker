import styled from '@emotion/styled';
import Button from '@/shared/components/Button';
import XIcon from '@/assets/icons/xSquare.svg?react';

interface NewIssueActionButtonsProps {
  isSubmitDisabled: boolean;
  onSubmit: () => void;
}

export default function NewIssueActionButtons({
  isSubmitDisabled,
  onSubmit,
}: NewIssueActionButtonsProps) {
  return (
    <ButtonGroup>
      <Button variant="ghost" size="medium" icon={<XIcon />}>
        작성 취소
      </Button>
      <Button size="medium" disabled={isSubmitDisabled} onClick={onSubmit}>
        완료
      </Button>
    </ButtonGroup>
  );
}

const ButtonGroup = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 32px;
`;
