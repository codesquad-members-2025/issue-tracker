import styled from '@emotion/styled';
import { useNavigate } from 'react-router-dom';
import { type Label } from '@/features/label/types';
import LabelBadgeList from '@/shared/components/LabelBadgeList';

interface Props {
  issueId: number;
  title: string;
  labels: Label[];
}

const TitleWithLabels = ({ issueId, title, labels }: Props) => {
  const navigate = useNavigate();

  const handleClickTitle = () => {
    navigate(`/issues/${issueId}`);
  };

  return (
    <TitleRow>
      <IssueTitle onClick={handleClickTitle}>{title}</IssueTitle>
      <LabelBadgeList labels={labels} />
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
