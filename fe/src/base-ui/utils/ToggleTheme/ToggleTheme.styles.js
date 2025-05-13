// DarkLightToggle.styles.js
import styled, { keyframes, css } from 'styled-components';

const rise = keyframes`
  from { transform: rotate(180deg); }
  to { transform: rotate(360deg); }
`;

const set = keyframes`
  from { transform: rotate(0deg); }
  to { transform: rotate(180deg); }
`;

export const ToggleButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  width: 50px;
  height: 50px;
  background-color: ${({ theme }) => theme.surface.default};
  border: none;
  border-radius: 10px;
  overflow: hidden;
  cursor: pointer;
  transition: background-color 0.5s;

  &:hover svg {
    transform: scale(1.2);
  }
`;

export const Icon = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 30px;
  height: 30px;
  position: absolute;
  transform-origin: 50% 200%;
  transition: transform 0.5s ease-in-out;

  svg {
    transition:
      fill 0.5s,
      transform 0.5s ease;

    &:hover {
      fill: gold;
    }
  }

  ${({ $type, $theme }) =>
    $type === 'sun' && $theme
      ? css`
          animation: ${set} 1s forwards;
        `
      : $type === 'sun' && !$theme
        ? css`
            animation: ${rise} 1s forwards;
          `
        : $type === 'moon' && $theme
          ? css`
              animation: ${rise} 1s forwards;
            `
          : css`
              animation: ${set} 1s forwards;
            `}
`;
