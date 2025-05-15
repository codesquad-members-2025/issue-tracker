import CloseIssueButton from '@/base-ui/issueListPage/IssueListHeader/CloseIssueButton';
import OpenIssueButton from '@/base-ui/issueListPage/IssueListHeader/OpenIssueButton';
import styled from 'styled-components';

const Container = styled.div`
  display: flex;
  gap: 24px;
`;

export default function IsOpenFilter({ openIssueNumber, closeIssueNumber }) {
  return (
    <Container>
      <OpenIssueButton number={openIssueNumber} />
      <CloseIssueButton number={closeIssueNumber} />
    </Container>
  );
}
