/** @jsxImportSource @emotion/react */
import styled from "@emotion/styled";

const LogoWrapper = styled.div`
  display: flex;
  align-items: center;
  font-family: "Pretendard", sans-serif;
  font-weight: 700;
  font-size: 2rem; /* 32px */
  color: ${({ theme }) => theme.colors.brandText.default};
`;

const SvgIcon = styled.svg`
  width: 2rem; /* 32px */
  height: 2rem;
`;

const Logo: React.FC = () => (
  <LogoWrapper>
    <SvgIcon viewBox="0 0 24 24" fill="currentColor">
      {/* 실제 로고 SVG 경로 */}
      <path d="M4 4h16v16H4z" />
    </SvgIcon>
    <span css={{ marginLeft: "0.5rem" }}>Issue Tracker</span>
  </LogoWrapper>
);

export default Logo;
