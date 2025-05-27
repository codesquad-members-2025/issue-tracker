import styled from '@emotion/styled';
import Button from '@/shared/components/Button';
import XIcon from '@/assets/icons/xSquare.svg?react';

export default function NewIssueActionButtons() {
  return (
    <ButtonGroup>
      <Button variant="ghost" size="medium" icon={<XIcon />}>
        작성 취소
      </Button>
      {/* TODO 조건에 따라 button 활성화 */}
      <Button size="medium" disabled={true}>
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
