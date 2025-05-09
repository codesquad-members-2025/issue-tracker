import styled from 'styled-components';

const Avatar = styled.img`
  width: 32px;
  height: 32px;
  border-radius: 50%;
`;

export default function UserAvatar({ avatarUrl }) {
  const altText = '아바타 이미지';
  return <Avatar src={avatarUrl} alt={altText} />;
}
