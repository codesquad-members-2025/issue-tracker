import styled from 'styled-components';
import { MilestoneButton, LabelButton } from '@/base-ui/issueListPage/mainPageHeaderTap/taps';
import { radius } from '@/styles/foundation';

const ButtonWrapper = styled.div`
  border: 1px solid ${({ theme }) => theme.border.default};
  border-radius: ${radius.medium};
  display: inline-flex;
  width: fit-content;
`;

export function NavigateTabs() {
  return (
    <ButtonWrapper>
      <LabelButton number={2} />
      <MilestoneButton number={3} />
    </ButtonWrapper>
  );
}
