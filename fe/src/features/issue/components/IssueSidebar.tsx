import styled from '@emotion/styled';
import { getAccessibleLabelStyle } from '@/shared/utils/color';
import { type Assignee, type Milestone, type Label } from '../types/issue';
import SidebarSection from './SidebarSection';
import MilestoneProgressBar from '@/shared/components/MilestoneProgressBar';

interface SidebarProps {
  assignees: Assignee[];
  labels: Label[];
  milestone: Milestone | null;
}

export default function IssueSidebar({
  assignees,
  labels,
  milestone,
}: SidebarProps) {
  return (
    <SidebarWrapper>
      <SidebarSection title="담당자" isEmpty={assignees.length === 0}>
        <SectionList>
          {assignees.map(assignee => (
            <AssigneeWrapper key={assignee.id}>
              <ProfileImage src={assignee.profileImage} alt="프로필 이미지" />
              <Nickname>{assignee.nickname}</Nickname>
            </AssigneeWrapper>
          ))}
        </SectionList>
      </SidebarSection>

      <SidebarSection title="레이블" isEmpty={assignees.length === 0}>
        <LabelList>{renderLabelList(labels)}</LabelList>
      </SidebarSection>

      <SidebarSection title="마일스톤" isEmpty={!milestone}>
        {milestone && (
          <MilestoneProgressBar percentage={milestone.progressRate}>
            <MilestoneLabel>{milestone.name}</MilestoneLabel>
          </MilestoneProgressBar>
        )}
      </SidebarSection>
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

const LabelList = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 4px;

  ${({ theme }) => theme.typography.availableMedium12};
  color: ${({ theme }) => theme.neutral.text.strong};
`;

const SectionList = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 16px;

  ${({ theme }) => theme.typography.availableMedium12};
  color: ${({ theme }) => theme.neutral.text.strong};
`;

// TODO profile 공통 컴포넌트 분리
const ProfileImage = styled.img`
  width: 20px;
  height: 19px;
  border-radius: ${({ theme }) => theme.radius.half};
`;

const Nickname = styled.span`
  ${({ theme }) => theme.typography.displayMedium16};
  color: ${({ theme }) => theme.neutral.text.default};
`;

const AssigneeWrapper = styled.div`
  display: flex;
  gap: 8px;
  align-items: center;
`;

const MilestoneLabel = styled.span`
  ${({ theme }) => theme.typography.displayMedium12};
  color: ${({ theme }) => theme.neutral.text.strong};
`;

//TODO 공용 라벨 컴포넌트 분리
interface LabelTagProps {
  backgroundColor: string;
  borderColor: string;
  color: string;
}

const LabelTag = styled.span<LabelTagProps>`
  padding: 4px 9px;
  border-radius: ${({ theme }) => theme.radius.medium};
  color: ${({ color }) => color};
  background-color: ${({ backgroundColor }) => backgroundColor};
  border: 1px solid ${({ borderColor }) => borderColor};
  ${({ theme }) => theme.typography.displayMedium12};
`;

//TODO 공용 분리
function renderLabelList(labels: Label[]) {
  return labels.map(label => {
    const { textColor, borderColor } = getAccessibleLabelStyle(label.color);
    return (
      <LabelTag
        key={label.id}
        backgroundColor={label.color}
        borderColor={borderColor}
        color={textColor}
      >
        {label.name}
      </LabelTag>
    );
  });
}
