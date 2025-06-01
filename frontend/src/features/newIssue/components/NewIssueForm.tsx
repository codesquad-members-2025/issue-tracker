import styled from '@emotion/styled';
import CommentInputSection from '@/features/newIssue/components/CommentInputSection';
import TitleInput from '@/features/newIssue/components/TitleInput';

interface NewIssueFormProps {
  title: string;
  onTitleChange: (value: string) => void;

  content: string;
  onContentChange: (value: string | ((prev: string) => string)) => void;
}

export default function NewIssueForm({
  title,
  content,
  onTitleChange,
  onContentChange,
}: NewIssueFormProps) {
  return (
    <FormSection>
      <TitleInput value={title} onChange={onTitleChange} label="제목" />
      <CommentInputSection
        value={content}
        onChange={onContentChange}
        initialHeight={448}
      />
    </FormSection>
  );
}

const FormSection = styled.section`
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 16px;
`;
