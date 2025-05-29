import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styled from '@emotion/styled';
import TrashIcon from '@/assets/icons/trash.svg?react';
import useIssueDetail from '@/features/issue/hooks/useIssueDetail';
import useIssueComments from '@/features/issue/hooks/useIssueComments';
import Divider from '@/shared/components/Divider';
import IssueHeader from '@/features/issue/components/detail/IssueHeader';
import IssueMainSection from '@/features/issue/components/detail/IssueMainSection';
import IssueSidebar from '@/features/issue/components/IssueSidebar';
import VerticalStack from '@/layouts/VerticalStack';

export default function IssueDetailPage() {
  const { id } = useParams();
  const issueId = Number(id);
  const navigate = useNavigate();

  const {
    data: issueDetail,
    isLoading: isIssueDetailLoading,
    isError: isIssueDetailError,
  } = useIssueDetail(issueId);

  const {
    data: commentList,
    isLoading: isCommentLoading,
    isError: isCommentError,
  } = useIssueComments(issueId);

  //TODO 에러 종류에 따라 분기 처리
  useEffect(() => {
    if (isIssueDetailError) {
      navigate('/404', { replace: true });
    }
  }, [isIssueDetailError]);

  // TODO 로딩,에러 상태에 따라 분기처리 내부적으로 처리
  if (isIssueDetailLoading || isCommentLoading) return <div>로딩 중...</div>;

  if (isIssueDetailError || isCommentError) return <div>에러 발생</div>;
  if (!issueDetail || !commentList) return;

  return (
    <VerticalStack>
      <IssueHeader
        {...issueDetail}
        issueNumber={issueDetail.id}
        commentCount={commentList.length}
      />
      <Divider />
      <MainArea>
        <IssueMainSection
          comments={commentList}
          content={issueDetail.content}
          createdAt={issueDetail.createdAt}
          author={issueDetail.author}
        />
        <SideSection>
          {/* TODO 이슈 상세 편집을 위한 상태 설계 후 사이드바 추가 */}
          {/* <IssueSidebar /> */}
          <TabItem icon={<TrashIcon />} label="이슈 삭제" />
        </SideSection>
      </MainArea>
    </VerticalStack>
  );
}

const MainArea = styled.div`
  display: flex;
  align-items: flex-start;
  gap: 32px;
`;

const SideSection = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 16px;
`;

interface TabItemProps {
  icon: React.ReactNode;
  label: string;
}

// TODO 공용으로 분리 및 onClick 추가
function TabItem({ icon, label }: TabItemProps) {
  return (
    <TabButton>
      <IconWrapper>{icon}</IconWrapper>
      <Label>{label}</Label>
    </TabButton>
  );
}

const TabButton = styled.button<{ active?: boolean }>`
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 8px 0;
  margin-right: 16px;
  color: ${({ theme }) => theme.danger.text.default};
  ${({ theme }) => theme.typography.availableMedium12};

  cursor: pointer;

  &:hover {
    opacity: 0.8;
  }
`;

const IconWrapper = styled.span`
  display: flex;
  align-items: center;
  color: inherit;

  svg {
    width: 16px;
    height: 16px;
  }
`;

const Label = styled.span`
  color: inherit;
`;
