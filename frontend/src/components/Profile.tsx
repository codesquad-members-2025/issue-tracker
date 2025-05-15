/** @jsxImportSource @emotion/react */
import styled from "@emotion/styled";

const Avatar = styled.img`
  width: 2.5rem; /* 40px */
  height: 2.5rem;
  border-radius: 50%;
  object-fit: cover;
  cursor: pointer;
`;

interface ProfileProps {
  avatarUrl: string;
}

const Profile: React.FC<ProfileProps> = ({ avatarUrl }) => (
  <Avatar src={avatarUrl} alt="프로필" />
);

export default Profile;
