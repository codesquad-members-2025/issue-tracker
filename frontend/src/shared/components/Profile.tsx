/** @jsxImportSource @emotion/react */
import styled from '@emotion/styled';
import { css, useTheme } from '@emotion/react';

interface ProfileProps {
  id?: number;
  size?: 'md' | 'sm';
  name?: string;
  imageUrl?: string;
  alt?: string;
}

const Profile: React.FC<ProfileProps> = ({
  id,
  size = 'md',
  name,
  imageUrl,
  alt = 'Profile image',
}) => {
  const theme = useTheme();
  const avatarSrc = imageUrl || '/images/sampleProfile.png';

  const variantConfig = {
    md: {
      avatarPx: 32,
      typo: 'displayMedium16',
      color: theme.neutral.text.default,
      gap: 8,
    },
    sm: {
      avatarPx: 20,
      typo: 'displayMedium12',
      color: theme.neutral.text.strong,
      gap: 8,
    },
  } as const;

  const { avatarPx, typo, color, gap } = variantConfig[size];
  const colorValue = color;

  return (
    <Wrapper key={id} gap={gap}>
      <Avatar src={avatarSrc} size={avatarPx} alt={alt} />
      {name && (
        <Name
          css={css`
            ${theme.typography[typo]};
            color: ${colorValue};
          `}
        >
          {name}
        </Name>
      )}
    </Wrapper>
  );
};

const Wrapper = styled.div<{ gap: number }>`
  display: flex;
  align-items: center;
  gap: ${({ gap }) => gap}px;
`;

const Avatar = styled.img<{ size: number }>`
  width: ${({ size }) => size}px;
  height: ${({ size }) => size}px;
  border-radius: 50%;
`;

const Name = styled.span``;

export default Profile;
