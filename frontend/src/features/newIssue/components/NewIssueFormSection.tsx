import styled from '@emotion/styled';
import Profile from '@/shared/components/Profile';
import IssueForm from './NewIssueForm';

interface NewIssueFormSectionProps {
  title: string;
  onTitleChange: (value: string) => void;

  content: string;
  onContentChange: (value: string) => void;

  milestoneId: number | null;
  onMilestoneChange: (id: number | null) => void;

  selectedLabelIds: number[];
  onToggleLabel: (id: number) => void;

  selectedAssigneeIds: number[];
  onToggleAssignee: (id: number) => void;
}

export default function NewIssueFormSection({
  title,
  onTitleChange,
  content,
  onContentChange,
}: NewIssueFormSectionProps) {
  return (
    <MainWrapper>
      <Profile size="md" />
      <IssueForm
        title={title}
        content={content}
        onTitleChange={onTitleChange}
        onContentChange={onContentChange}
      />
      {/* sidebar */}
    </MainWrapper>
  );
}

const MainWrapper = styled.main`
  display: flex;
  align-items: flex-start;
  gap: 24px;
`;
