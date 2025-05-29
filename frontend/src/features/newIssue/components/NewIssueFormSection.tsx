import styled from '@emotion/styled';
import Profile from '@/shared/components/Profile';
import IssueForm from './NewIssueForm';
import IssueSidebar from '@/features/issue/components/IssueSidebar';

interface NewIssueFormSectionProps {
  title: string;
  onTitleChange: (value: string) => void;

  content: string;
  onContentChange: (value: string) => void;

  milestoneId: number | null;
  onMilestoneChange: (id: number) => void;

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
  milestoneId,
  onMilestoneChange,
  selectedLabelIds,
  onToggleLabel,
  selectedAssigneeIds,
  onToggleAssignee,
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
      <IssueSidebar
        selectedAssigneeIds={selectedAssigneeIds}
        onToggleAssignee={onToggleAssignee}
        selectedLabelIds={selectedLabelIds}
        onToggleLabel={onToggleLabel}
        selectedMilestoneId={milestoneId}
        onSelectMilestone={onMilestoneChange}
      />
    </MainWrapper>
  );
}

const MainWrapper = styled.main`
  display: flex;
  align-items: flex-start;
  gap: 24px;
`;
