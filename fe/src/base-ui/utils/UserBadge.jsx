import styled from 'styled-components';

const Avatar = styled.img`
  width: 20px;
  height: 20px;
  border-radius: 50%;
`;

export default function UserAvatar({ avatarUrl }) {
  const altText = '유저 뱃지 이미지';
  return <Avatar src={avatarUrl} alt={altText} />;
}
