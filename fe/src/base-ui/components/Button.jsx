// components/ui/Button.jsx
import styled from 'styled-components';
import { typography } from '@/styles/foundation';

const GhostButton = styled.button`
  ${typography.available.medium16}
  all: unset;
  display: flex;
  align-items: center;
  padding: 4px 0px;
  background-color: transparent;
  color: ${({ theme }) => theme.text.default};
  cursor: pointer;

  &:hover {
    color: ${({ theme }) => theme.text.strong};
  }

  &:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }
`;

export default GhostButton;
