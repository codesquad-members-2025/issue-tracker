import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import Profile from '@/shared/components/Profile';
import Logo from '@/assets/logoMedium.svg?react';
import DarkModeToggle from '@/shared/components/DarkModeToggle';

export default function Header() {
  return (
    <Wrapper>
      <LeftSection>
        <Link to="/">
          <StyledLogo />
        </Link>
        <DarkModeToggle />
      </LeftSection>
      {/* 로그인 기능 구현시 유저 정보(이미지,id등) 전달 */}
      <Profile size="md" />
    </Wrapper>
  );
}

const Wrapper = styled.header`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 27px 0;
`;

const LeftSection = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
`;

const StyledLogo = styled(Logo)`
  width: 199px;
  height: 40px;
  color: ${({ theme }) => theme.neutral.text.strong};
`;
