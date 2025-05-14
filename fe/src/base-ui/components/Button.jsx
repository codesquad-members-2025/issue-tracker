// components/ui/Button.jsx
import styled, { css } from 'styled-components';
import { typography } from '@/styles/foundation';

const ghostButtonStyle = css`
  ${typography.available.medium16}
  all: unset;
  display: flex;
  align-items: center;
  background-color: transparent;
  color: ${({ theme }) => theme.text.default};
  cursor: pointer;

  &:hover {
    color: ${({ theme }) => theme.text.strong};
    ${typography.selected.bold16}
    svg {
      path {
        stroke: ${({ theme }) => theme.text.strong};
      }
    }
  }

  svg path {
    stroke: ${({ theme }) => theme.text.default};
    transition: stroke 0.2s ease;
  }

  &:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }
`;

const GhostButton = styled.button`
  ${ghostButtonStyle}
`;

export { GhostButton, ghostButtonStyle };
