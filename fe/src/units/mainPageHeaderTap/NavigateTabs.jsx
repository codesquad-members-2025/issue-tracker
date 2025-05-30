import styled from 'styled-components';
import { MilestoneButton, LabelButton } from '@/base-ui/issueListPage/mainPageHeaderTap/taps';
import { radius } from '@/styles/foundation';
import { SmallContainerButton } from '@/base-ui/components/ContainerButtons';
import { useNavigate } from 'react-router-dom';
import useIssueDetailStore from '@/stores/IssueDetailStore';

const Container = styled.div`
  display: flex;
  gap: 16px;
`;

const NewIssueButton = styled(SmallContainerButton)`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
`;

const ButtonWrapper = styled.div`
  border: 1px solid ${({ theme }) => theme.border.default};
  border-radius: ${radius.medium};
  display: inline-flex;
  width: fit-content;
`;

export function NavigateTabs() {
  const resetStore = useIssueDetailStore((s) => s.resetStore);
  const navigate = useNavigate();
  const buttonLabel = '이슈작성';
  return (
    <Container>
      <ButtonWrapper>
        <LabelButton number={2} />
        <MilestoneButton number={3} />
      </ButtonWrapper>
      <NewIssueButton
        onClick={() => {
          navigate('new');
          resetStore();
        }}
      >
        <svg
          width="17"
          height="16"
          viewBox="0 0 17 16"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M8.5 3.33337V12.6667"
            stroke="#FEFEFE"
            strokeWidth="1.6"
            strokeLinecap="round"
            strokeLinejoin="round"
          />
          <path
            d="M3.8335 8H13.1668"
            stroke="#FEFEFE"
            strokeWidth="1.6"
            strokeLinecap="round"
            strokeLinejoin="round"
          />
        </svg>
        <span>{buttonLabel}</span>
      </NewIssueButton>
    </Container>
  );
}
