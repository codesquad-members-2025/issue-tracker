import styled from 'styled-components';
import { typography } from '@/styles/foundation';

const StyledButton = styled.button`
  ${typography.available.medium20}
  color:${({ theme }) => theme.brand.text.weak};
  border: 1px solid ${({ theme }) => theme.brand.border.default};
  border-radius: 16px;
  padding: 12px 62px;

  &:hover {
    opacity: 0.8;
  }
  &:active {
    opacity: 0.64;
  }
`;

export default function GitHubLoginButton({ onClick }) {
  const butotnLabel = 'GitHub 계정으로 로그인';
  return <StyledButton onClick={onClick}>{butotnLabel}</StyledButton>;
}
