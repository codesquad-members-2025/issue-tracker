import styled from '@emotion/styled';
import { type ReactNode } from 'react';

interface MilestoneProgressBarProps {
  percentage: number;
  height?: number;
  children?: ReactNode;
}

const MilestoneProgressBar = ({
  percentage,
  height = 8,
  children,
}: MilestoneProgressBarProps) => {
  return (
    <Wrapper>
      <Track height={height}>
        <Fill width={percentage} />
      </Track>
      {children && <ContentWrapper>{children}</ContentWrapper>}
    </Wrapper>
  );
};

export default MilestoneProgressBar;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

const Track = styled.div<{ height: number }>`
  width: 100%;
  height: ${({ height }) => height}px;
  background-color: ${({ theme }) => theme.neutral.surface.bold};
  border-radius: 4px;
  overflow: hidden;
`;

const Fill = styled.div<{ width: number }>`
  width: ${({ width }) => width}%;
  height: 100%;
  background-color: ${({ theme }) => theme.palette.blue};
  transition: width 0.3s ease;
`;

const ContentWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  ${({ theme }) => theme.typography.availableMedium12};
  color: ${({ theme }) => theme.neutral.text.weak};
`;
