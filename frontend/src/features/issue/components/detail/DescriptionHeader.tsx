import styled from '@emotion/styled';
import { getTimeAgoString } from '@/shared/utils/time';
import type { User } from '@/features/user/types';
import Button from '@/shared/components/Button';
import Profile from '@/shared/components/Profile';
import EditIcon from '@/assets/icons/edit.svg?react';
import SmileIcon from '@/assets/icons/smile.svg?react';

interface DescriptionHeaderProps {
  author: User;
  createdAt: string;
  isAuthor: boolean;
}

export default function DescriptionHeader({
  author,
  createdAt,
  isAuthor = false,
}: DescriptionHeaderProps) {
  const { id, nickname, profileImage } = author;
  return (
    <Container>
      <LeftSection>
        <Profile id={id} size="md" name={nickname} imageUrl={profileImage} />
        <CreatedAt>{getTimeAgoString(createdAt)}</CreatedAt>
      </LeftSection>

      <RightSection>
        {isAuthor && <AuthorTag>작성자</AuthorTag>}
        <Button
          variant="ghost"
          size="small"
          icon={<EditIcon />}
          onClick={() => {
            /* 편집 로직 */
          }}
        >
          편집
        </Button>

        <Button
          variant="ghost"
          size="small"
          icon={<SmileIcon />}
          onClick={() => {
            /* 반응 로직 */
          }}
        >
          반응
        </Button>
      </RightSection>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 24px;
  border: 1px solid ${({ theme }) => theme.neutral.surface.default};
`;

const LeftSection = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

const RightSection = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
`;

const CreatedAt = styled.span`
  ${({ theme }) => theme.typography.displayMedium16};
  color: ${({ theme }) => theme.neutral.text.weak};
`;

const AuthorTag = styled.span`
  padding: 4px 12px;
  background-color: ${({ theme }) => theme.neutral.surface.bold};
  border: 1px solid ${({ theme }) => theme.neutral.border.default};
  border-radius: ${({ theme }) => theme.radius.medium};
  ${({ theme }) => theme.typography.displayMedium12};
  color: ${({ theme }) => theme.neutral.text.weak};
`;
