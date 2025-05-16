// styles/GlobalStyle.js
import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  

  /* 2. Pretendard 적용 */
  html, body {
    font-family: 'Pretendard Variable', sans-serif;
    background-color: ${({ theme }) => theme.surface.default};
    color: ${({ theme }) => theme.text.default};
  }

  button, input, textarea {
    font-family: inherit;
    border: none;
    outline: none;
    background: none;
    padding:0px;
  }

  a {
    text-decoration: none;
    color: inherit;
  }

  /* 1. 기본 Reset */
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }
`;

export default GlobalStyle;
