import styled from '@emotion/styled';

interface LabelBadgeProps {
  name: string;
  color: string;
  textColor: string;
  borderColor: string;
}

export default function LabelBadge({
  name,
  color,
  textColor,
  borderColor,
}: LabelBadgeProps) {
  return (
    <LabelWrapper
      backgroundColor={color}
      color={textColor}
      borderColor={borderColor}
    >
      <span>{name}</span>
    </LabelWrapper>
  );
}

const LabelWrapper = styled.div<{
  backgroundColor: string;
  color: string;
  borderColor: string;
}>`
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 4px 12px;
  border-radius: ${({ theme }) => theme.radius.medium};
  background-color: ${({ backgroundColor }) => backgroundColor};
  color: ${({ color }) => color};
  box-shadow: ${({ borderColor }) => `0 0 0 1px ${borderColor}`};
  ${({ theme }) => theme.typography.displayMedium12};
`;
