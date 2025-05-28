import styled from '@emotion/styled';

export default function NewIssueHeader() {
  return (
    <HeaderWrapper>
      <Title>새로운 이슈 작성</Title>
    </HeaderWrapper>
  );
}

const HeaderWrapper = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Title = styled.h1`
  display: flex;
  align-items: flex-start;
  gap: 8px;

  color: ${({ theme }) => theme.neutral.text.strong};
  ${({ theme }) => theme.typography.displayBold32};
`;
