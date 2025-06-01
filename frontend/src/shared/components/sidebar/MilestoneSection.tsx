import styled from '@emotion/styled';
import type { MilestoneDetail } from '@/features/milestone/types';
import useMilestones from '@/features/milestone/hooks/useMilestones';
import Dropdown from '@/shared/components/Dropdown';
import DropdownPanel from '@/shared/components/DropdownPanel';
import MilestoneProgressBar from '@/shared/components/MilestoneProgressBar';

interface MilestoneSectionProps {
  selectedMilestoneId: number | null;
  onSelectMilestone: (id: number) => void;
}

export default function MilestoneSection({
  selectedMilestoneId,
  onSelectMilestone,
}: MilestoneSectionProps) {
  const { milestones } = useMilestones();

  const milestoneOptions = milestones.map((milestone: MilestoneDetail) => ({
    id: milestone.id,
    name: milestone.title,
    progressRate: milestone.progressRate,
    selected: selectedMilestoneId === milestone.id,
  }));

  const selectedMilestone = milestones.find(
    (milestone: MilestoneDetail) => selectedMilestoneId === milestone.id,
  );

  return (
    <Section>
      <Dropdown label="마엘스톤">
        <DropdownPanel<{ progressRate: number }>
          options={milestoneOptions}
          onSelect={onSelectMilestone}
          renderOption={option => <span>{option.name}</span>}
        />
      </Dropdown>

      {selectedMilestone && (
        <SectionList>
          <MilestoneProgressBar percentage={selectedMilestone.progressRate}>
            <MilestoneLabel>{selectedMilestone.title}</MilestoneLabel>
          </MilestoneProgressBar>
        </SectionList>
      )}
    </Section>
  );
}

const Section = styled.div<{ noDivider?: boolean }>`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 16px;
  padding: 32px;
`;

const SectionList = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 16px;

  ${({ theme }) => theme.typography.availableMedium12};
  color: ${({ theme }) => theme.neutral.text.strong};
`;

const MilestoneLabel = styled.span`
  ${({ theme }) => theme.typography.displayMedium12};
  color: ${({ theme }) => theme.neutral.text.strong};
`;
