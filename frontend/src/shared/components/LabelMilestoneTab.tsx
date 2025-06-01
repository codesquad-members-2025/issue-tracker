import { useNavigate } from 'react-router-dom';
import TabButton from '@/shared/components/TabButton';
import TabItem from '@/shared/components/TabItem';
import MilestoneIcon from '@/assets/icons/milestone.svg?react';
import LabelIcon from '@/assets/icons/label.svg?react';

interface LabelMilestoneTabProps {
  labelCount: number;
  milestoneCount: number;
}

export default function LabelMilestoneTab({
  labelCount,
  milestoneCount,
}: LabelMilestoneTabProps) {
  const navigate = useNavigate();

  const labelTab = (
    <TabItem
      icon={<LabelIcon width={16} />}
      label="레이블"
      count={labelCount}
      onClick={() => navigate('/labels')}
    />
  );

  const milestoneTab = (
    <TabItem
      icon={<MilestoneIcon width={16} />}
      label="마일스톤"
      count={milestoneCount}
      onClick={() => navigate('/milestones')}
    />
  );

  return <TabButton left={labelTab} right={milestoneTab} />;
}
