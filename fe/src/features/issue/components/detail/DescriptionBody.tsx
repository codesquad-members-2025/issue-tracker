import styled from '@emotion/styled';

interface DescriptionBodyProps {
  content: string;
}

export default function DescriptionBody({ content }: DescriptionBodyProps) {
  return <StyledContent>{content}</StyledContent>;
}

const StyledContent = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 16px 24px 24px 24px;
  background-color: ${({ theme }) => theme.neutral.surface.strong};
  border-top: 1px solid ${({ theme }) => theme.neutral.border.default};
  ${({ theme }) => theme.typography.displayMedium16};
  color: ${({ theme }) => theme.neutral.text.default};
`;
