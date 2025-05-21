import styled from '@emotion/styled';
import { formatCreatedMessage } from '@/features/issue/utils';
import ClosedIcon from '@/assets/icons/archive.svg?react';
import EditIcon from '@/assets/icons/edit.svg?react';
import AlertCircleIcon from '@/assets/icons/alertCircle.svg?react';

interface IssueHeaderProps {
  isClosed: boolean;
  issueNumber: number;
  title: string;
  author: {
    id: number;
    nickname: string;
    profileImage: string;
  };
  createdAt: string;
  commentCount: number;
}

//TODO 편집 기능, 이슈 열림 토글 기능 추가시 prop도 추가
export default function IssueHeader({
  isClosed,
  issueNumber,
  title,
  author,
  createdAt,
  commentCount,
}: IssueHeaderProps) {
  return (
    <HeaderWrapper>
      <LeftSection>
        <Title>
          {title} <IssueNumber>#{issueNumber}</IssueNumber>
        </Title>
        <IssueMetaWrapper>
          <IsClosedButton>
            <AlertCircleIcon />
            <StateBadge>{isClosed ? '닫힌 이슈' : '열린 이슈'}</StateBadge>
          </IsClosedButton>
          <MetaInfo>
            {formatCreatedMessage(createdAt, author.nickname)}
          </MetaInfo>
          <CommentCount>코멘트 {commentCount}개</CommentCount>
        </IssueMetaWrapper>
      </LeftSection>
      <RightSection>
        <StyledButton>
          <EditIcon />
          <StyledLabel>제목 편집</StyledLabel>
        </StyledButton>
        <StyledButton>
          <ClosedIcon />
          <StyledLabel>{isClosed ? '이슈 열기' : '이슈 닫기'}</StyledLabel>
        </StyledButton>
      </RightSection>
    </HeaderWrapper>
  );
}

const HeaderWrapper = styled.div`
  display: flex;
  justify-content: space-between;
`;

const LeftSection = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

const IssueMetaWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  flex-wrap: wrap;
`;

const RightSection = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
  height: 48px;
`;

const Title = styled.h1`
  display: flex;
  align-items: flex-start;
  gap: 8px;

  color: ${({ theme }) => theme.neutral.text.strong};
  ${({ theme }) => theme.typography.displayBold32};
`;

const IssueNumber = styled.span`
  color: ${({ theme }) => theme.neutral.text.weak};
`;

const MetaInfo = styled.span`
  color: ${({ theme }) => theme.neutral.text.weak};
  ${({ theme }) => theme.typography.displayMedium16};
`;

const CommentCount = styled.span`
  margin-left: 24px;
  color: ${({ theme }) => theme.neutral.text.weak};
  ${({ theme }) => theme.typography.displayMedium16};
`;

//TODO 공용 버튼 생성시 StyledButton, IsClosedButton 통합
const StyledButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 128px;
  height: 40px;
  padding: 12px 0;
  border: 1px solid ${({ theme }) => theme.palette.blue};
  border-radius: ${({ theme }) => theme.radius.medium};
  color: ${({ theme }) => theme.palette.blue};
  ${({ theme }) => theme.typography.availableMedium12};

  cursor: pointer;
`;

const StyledLabel = styled.span`
  padding: 0 4px;
  ${({ theme }) => theme.typography.availableMedium12};
`;

const IsClosedButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 8px 16px;
  background-color: ${({ theme }) => theme.palette.blue};
  border-radius: ${({ theme }) => theme.radius.large};
  color: ${({ theme }) => theme.brand.text.default};

  cursor: pointer;
  transition: all 0.2s ease;

  &:hover {
    opacity: 0.8;
  }
`;

const StateBadge = styled.span`
  padding: 0 4px;
  color: ${({ theme }) => theme.brand.text.default};
  ${({ theme }) => theme.typography.displayMedium12};
`;
