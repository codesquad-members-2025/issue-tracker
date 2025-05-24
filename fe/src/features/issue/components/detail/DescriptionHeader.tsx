import styled from '@emotion/styled';
import { getTimeAgoString } from '@/shared/utils/time';
import type { Author } from '../../types/issue';
import Profile from '@/shared/components/Profile';
import EditIcon from '@/assets/icons/edit.svg?react';
import SmileIcon from '@/assets/icons/smile.svg?react';

interface DescriptionHeaderProps {
  author: Author;
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
        <IconButton variant="edit">
          <EditIcon />
          편집
        </IconButton>
        <IconButton variant="reaction">
          <SmileIcon />
          반응
        </IconButton>
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

//TODO 공용 버튼 컴포넌트 구현시 통합
const IconButton = styled.button<{ variant: 'edit' | 'reaction' }>`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  height: 100%;
  color: ${({ theme }) => theme.neutral.text.default};
  ${({ theme }) => theme.typography.availableMedium12};

  cursor: pointer;
`;
