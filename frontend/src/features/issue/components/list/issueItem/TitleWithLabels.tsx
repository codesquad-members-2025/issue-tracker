import styled from '@emotion/styled';
import { useNavigate } from 'react-router-dom';
import { getAccessibleLabelStyle } from '@/shared/utils/color';
import { type Label } from '@/features/label/types';

interface Props {
  issueId: number;
  title: string;
  labels: Label[];
}

interface LabelTagProps {
  backgroundColor: string;
  borderColor: string;
  color: string;
}

const TitleWithLabels = ({ issueId, title, labels }: Props) => {
  const navigate = useNavigate();

  const handleClickTitle = () => {
    navigate(`/issues/${issueId}`);
  };

  return (
    <TitleRow>
      <IssueTitle onClick={handleClickTitle}>{title}</IssueTitle>
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

  &:hover {
    text-decoration: underline;
  }
`;

const LabelTag = styled.span<LabelTagProps>`
  padding: 4px 9px;
  border-radius: ${({ theme }) => theme.radius.medium};
  color: ${({ color }) => color};
  background-color: ${({ backgroundColor }) => backgroundColor};
  border: 1px solid ${({ borderColor }) => borderColor};
  ${({ theme }) => theme.typography.displayMedium12};
`;
