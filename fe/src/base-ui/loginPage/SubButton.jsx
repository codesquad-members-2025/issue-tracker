import styled from 'styled-components';
import { typography } from '@/styles/foundation';

const StyledButton = styled.button`
  ${typography.available.medium16};
  width: 320px;
  height: 40px;
  background-color: ${({ theme }) => theme.surface.default};
  color: ${({ theme }) => theme.text.default};
  border: none;
  border-radius: 16px;
  cursor: pointer;
  &:hover {
    background-color: ${({ theme }) => theme.surface.bold};
  }
`;

export default function SubButton({ onClick, buttonLabelText }) {
  return <StyledButton onClick={onClick}>{buttonLabelText}</StyledButton>;
}
