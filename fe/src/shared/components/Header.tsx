import styled from '@emotion/styled';
import Logo from '@/assets/logoMedium.svg?react';
import DarkModeToggle from './DarkModeToggle';

export default function Header() {
  return (
    <Wrapper>
      <LeftSection>
        <StyledLogo />
        <DarkModeToggle />
      </LeftSection>
      <Profile profileImageUrl="/images/sampleProfile.png" />
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

// TODO profile 공통 컴포넌트 분리
interface Props {
  profileImageUrl: string;
}

const Profile = ({ profileImageUrl }: Props) => {
  return <Avatar src={profileImageUrl} alt="user-profile" />;
};

const Avatar = styled.img`
  width: 32px;
  height: 32px;
  border-radius: 50%;
  border: 1px solid white;
  object-fit: cover;
`;
