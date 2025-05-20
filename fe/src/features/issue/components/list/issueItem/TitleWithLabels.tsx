import styled from '@emotion/styled';
import { getAccessibleLabelStyle } from '@/shared/utils/color';
import { type Label } from '../../../types/issue';

interface Props {
  title: string;
  labels: Label[];
}

interface LabelTagProps {
  backgroundColor: string;
  borderColor: string;
  color: string;
}

const TitleWithLabels = ({ title, labels }: Props) => {
  return (
    <TitleRow>
      <IssueTitle>{title}</IssueTitle>
      {labels.map(label => {
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
      })}
    </TitleRow>
  );
};

export default TitleWithLabels;

const TitleRow = styled.div`
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 8px;
`;

const IssueTitle = styled.span`
  color: ${({ theme }) => theme.neutral.text.strong};
  ${({ theme }) => theme.typography.availableMedium20};
  cursor: pointer;
`;

const LabelTag = styled.span<LabelTagProps>`
  padding: 4px 9px;
  border-radius: ${({ theme }) => theme.radius.medium};
  color: ${({ color }) => color};
  background-color: ${({ backgroundColor }) => backgroundColor};
  border: 1px solid ${({ borderColor }) => borderColor};
  ${({ theme }) => theme.typography.displayMedium12};
`;
