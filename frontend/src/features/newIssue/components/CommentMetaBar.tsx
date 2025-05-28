import styled from '@emotion/styled';
import Button from '@/shared/components/Button';
import PaperClipIcon from '@/assets/icons/paperclip.svg?react';
import GripIcon from '@/assets/icons/grip.svg?react';

interface CommentMetaBarProps {
  showCharCount: boolean;
  charCount: number;
  onUploadClick: () => void;
  onFileChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  fileInputRef: React.RefObject<HTMLInputElement | null>;
}

export default function CommentMetaBar({
  showCharCount,
  charCount,
  onUploadClick,
  onFileChange,
  fileInputRef,
}: CommentMetaBarProps) {
  return (
    <BottomMetaWrapper>
      <LeftMeta>
        <CharCount visible={showCharCount}>
          띄어쓰기 포함 {charCount}자
        </CharCount>
        <GripIcon />
      </LeftMeta>
      <RightMeta>
        <Button
          variant="ghost"
          size="small"
          icon={<PaperClipIcon />}
          onClick={onUploadClick}
        >
          파일 첨부하기
        </Button>

        <input
          ref={fileInputRef}
          id="fileInput"
          type="file"
          accept="image/*"
          style={{ display: 'none' }}
          onChange={onFileChange}
        />
      </RightMeta>
    </BottomMetaWrapper>
  );
}

const BottomMetaWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 0 16px;
`;

const LeftMeta = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 8px;
  padding: 16px 0;

  color: ${({ theme }) => theme.neutral.text.weak};
`;

const CharCount = styled.span<{ visible: boolean }>`
  display: inline-block;
  color: ${({ theme }) => theme.neutral.text.weak};
  opacity: ${({ visible }) => (visible ? 1 : 0)};
  transition:
    opacity 0.3s ease,
    transform 0.3s ease;

  ${({ theme }) => theme.typography.displayMedium12};
`;

const RightMeta = styled.div`
  padding: 10px 0;
  border-top: ${({ theme }) =>
    `${theme.borderStyle.dash} ${theme.neutral.border.default}`};
`;
