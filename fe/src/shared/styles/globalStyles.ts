import { css, type Theme } from '@emotion/react';
import resetStyles from './reset.ts';

const globalStyle = (theme: Theme) => css`
  @import url('https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/variable/pretendardvariable.min.css');

  ${resetStyles};

  button {
    cursor: pointer;
  }

  body {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: stretch;
    justify-content: flex-start;
    font-family: ${theme.typography.fontFamily};
    background-color: ${theme.surfaceColor.default};
    color: ${theme.textColor.strong};

    transition:
      background-color 0.2s ease,
      color 0.2s ease;
  }

  html,
  body,
  #root {
    height: 100%;
    margin: 0;
    padding: 0;
  }
`;

export default globalStyle;
