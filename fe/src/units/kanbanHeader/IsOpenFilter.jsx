import CloseIssueButton from '@/base-ui/issueListPage/IssueListHeader/CloseIssueButton';
import OpenIssueButton from '@/base-ui/issueListPage/IssueListHeader/OpenIssueButton';
import styled from 'styled-components';
import useIssuesStore from '@/stores/issuesStore';

const Container = styled.div`
  display: flex;
  gap: 24px;
`;

export default function IsOpenFilter({ openIssueNumber, closeIssueNumber }) {
  const metaData = useIssuesStore((state) => state.metaData);
  return (
    <Container>
      <OpenIssueButton number={metaData.openIssueNumber} />
      <CloseIssueButton number={metaData.closeIssueNumber} />
    </Container>
  );
}
