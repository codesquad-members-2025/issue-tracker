import { Global, css } from "@emotion/react";

const base = css`
  *,
  *::before,
  *::after {
    box-sizing: border-box;
  }
  html,
  body {
    margin: 0;
    padding: 0;
  }
  body {
    font-family: "Pretendard", -apple-system, BlinkMacSystemFont, "Segoe UI",
      Roboto, Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue",
      sans-serif;
    background: var(--bg);
    color: var(--fg);
    transition: background 0.2s ease, color 0.2s ease;
  }
`;

export default function GlobalStyles() {
  return <Global styles={base} />;
}
