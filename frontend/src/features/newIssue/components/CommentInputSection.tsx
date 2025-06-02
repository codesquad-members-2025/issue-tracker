/** @jsxImportSource @emotion/react */
import { useEffect, useRef, useState } from 'react';
import styled from '@emotion/styled';
import uploadToS3 from '@/features/newIssue/apis/uploadToS3';
import LabelAbove from '@/shared/components/LabelAbove';
import CommentMetaBar from '@/features/newIssue/components/CommentMetaBar';

const DEFAULT_HEIGTH = 80;

interface CommentInputSectionProps {
  value: string;
  onChange: (value: string | ((prev: string) => string)) => void;
  initialHeight?: number;
  maxHeight?: number;
}

export default function CommentInputSection({
  value,
  onChange,
  initialHeight = DEFAULT_HEIGTH,
  maxHeight,
}: CommentInputSectionProps) {
  const [isFocused, setIsFocused] = useState(false);
  const [showCharCount, setShowCharCount] = useState(false);
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const timeoutRef = useRef<ReturnType<typeof setTimeout> | null>(null);
  const fileInputRef = useRef<HTMLInputElement>(null);

  const resizeTextarea = () => {
    if (textareaRef.current) {
      const minHeight = initialHeight;

      textareaRef.current.style.height = 'auto';
      const scrollHeight = textareaRef.current.scrollHeight;

      if (scrollHeight > minHeight) {
        textareaRef.current.style.height = `${scrollHeight}px`;
      } else {
        textareaRef.current.style.height = `${minHeight}px`;
      }
    }
  };

  const handleFileUpload = async () => {
    const files = fileInputRef.current?.files;
    if (!files || files.length === 0) return;

    const fileArray = Array.from(files);

    try {
      const urls = await Promise.all(fileArray.map(file => uploadToS3(file)));

      const markdownStrings = urls.map(url => `![image](${url})`);
      const markdownToInsert = markdownStrings.join('\n');

      onChange(prev => prev + '\n' + markdownToInsert);
    } catch (err) {
      console.error('파일 업로드 실패:', err);
      // TODO 사용자에게 에러 메시지 표시
    }

    if (fileInputRef.current) {
      fileInputRef.current.value = '';
    }
  };

  const handleCharCountVisibility = (value: string) => {
    if (!value) {
      setShowCharCount(false);
      return;
    }

    setShowCharCount(true);

    if (timeoutRef.current) clearTimeout(timeoutRef.current);
    timeoutRef.current = setTimeout(() => {
      setShowCharCount(false);
    }, 2000);
  };

  useEffect(() => {
    handleCharCountVisibility(value);
    resizeTextarea();
  }, [value]);

  return (
    <EditorContainer isFocused={isFocused}>
      <LabelAbove isFloating={isFocused || !!value}>
        코멘트를 입력하세요
      </LabelAbove>
      <ContentTextarea
        ref={textareaRef}
        value={value}
        onChange={e => onChange(e.target.value)}
        onFocus={() => setIsFocused(true)}
        onBlur={() => setIsFocused(false)}
        initialHeight={initialHeight}
        maxHeight={maxHeight}
      />

      <CommentMetaBar
        showCharCount={showCharCount}
        charCount={value.length}
        onUploadClick={() => fileInputRef.current?.click()}
        onFileChange={handleFileUpload}
        fileInputRef={fileInputRef}
      />
    </EditorContainer>
  );
}

const EditorContainer = styled.section<{ isFocused: boolean }>`
  position: relative;
  height: auto;
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 12px;
  border-radius: ${({ theme }) => theme.radius.medium};
  overflow: hidden;

  background-color: ${({ isFocused, theme }) =>
    isFocused ? theme.neutral.surface.default : theme.neutral.surface.bold};

  box-shadow: ${({ isFocused, theme }) =>
    isFocused ? `0 0 0 1px ${theme.neutral.border.active}` : 'none'};
`;

const ContentTextarea = styled.textarea<{
  initialHeight?: number;
  maxHeight?: number;
}>`
  width: 100%;
  padding: 28px 16px 16px;
  border: none;
  background-color: transparent;
  color: ${({ theme }) => theme.neutral.text.strong};
  resize: none;
  overflow: hidden;
  height: ${({ initialHeight }) =>
    initialHeight ? `${initialHeight}px` : 'auto'};
  max-height: ${({ maxHeight }) => (maxHeight ? `${maxHeight}px` : 'none')};

  ${({ theme }) => theme.typography.displayMedium16};

  &::placeholder {
    color: ${({ theme }) => theme.neutral.text.weak};
  }

  &:focus {
    outline: none;
  }
`;
