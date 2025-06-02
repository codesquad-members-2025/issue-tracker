import styled from '@emotion/styled';
import MilestoneIcon from '@/assets/icons/milestone.svg?react';
import { formatCreatedMessage } from '@/features/issue/utils';

interface Props {
  id: number;
  createdAt: string;
  author: string;
  milestone: string | null;
  isClosed: boolean;
}

const MetaInfo = ({ id, createdAt, author, milestone, isClosed }: Props) => {
  return (
    <Wrapper>
      <span>#{id}</span>
      <span>{formatCreatedMessage({ createdAt, author, isClosed })}</span>
      {milestone && (
        <MilestoneWrapper>
          <MilestoneIcon />
          <Milestone>{milestone}</Milestone>
        </MilestoneWrapper>
      )}
    </Wrapper>
  );
};

export default MetaInfo;

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
  color: ${({ theme }) => theme.neutral.text.weak};
  ${({ theme }) => theme.typography.displayMedium16};
`;

const MilestoneWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

const Milestone = styled.span`
  display: flex;
  align-items: center;
`;
