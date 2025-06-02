import styled from '@emotion/styled';
import AssigneeSection from '@/shared/components/sidebar/AssigneeSection';
import LabelSection from '@/shared/components/sidebar/LabelSection';
import MilestoneSection from '@/shared/components/sidebar/MilestoneSection';

interface IssueSidebarProps {
  selectedAssigneeIds: number[];
  onToggleAssignee: (id: number) => void;

  selectedLabelIds: number[];
  onToggleLabel: (id: number) => void;

  selectedMilestoneId: number | null;
  onSelectMilestone: (id: number) => void;
}

export default function IssueSidebar({
  selectedAssigneeIds,
  onToggleAssignee,
  selectedLabelIds,
  onToggleLabel,
  selectedMilestoneId,
  onSelectMilestone,
}: IssueSidebarProps) {
  return (
    <SidebarWrapper>
      <AssigneeSection
        selectedAssigneeIds={selectedAssigneeIds}
        onToggleAssignee={onToggleAssignee}
      />

      <LabelSection
        selectedLabelIds={selectedLabelIds}
        onToggleLabel={onToggleLabel}
      />

      <MilestoneSection
        selectedMilestoneId={selectedMilestoneId}
        onSelectMilestone={onSelectMilestone}
      />
    </SidebarWrapper>
  );
}

const SidebarWrapper = styled.div`
  width: 288px;
  position: relative;
  display: flex;
  flex-direction: column;
  border-radius: ${({ theme }) => theme.radius.medium};
  border: 1px solid ${({ theme }) => theme.neutral.border.default};
  background-color: ${({ theme }) => theme.neutral.surface.strong};

  overflow: hidden;

  & > *:not(:last-child) {
    border-bottom: 1px solid ${({ theme }) => theme.neutral.border.default};
  }
`;
