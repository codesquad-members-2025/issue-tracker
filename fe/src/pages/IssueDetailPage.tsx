import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styled from '@emotion/styled';
import useIssueDetail from '@/features/issue/hooks/useIssueDetail';
import useIssueComments from '@/features/issue/hooks/useIssueComments';
import Divider from '@/shared/components/Divider';
import IssueHeader from '@/features/issue/components/detail/IssueHeader';
import IssueMainSection from '@/features/issue/components/detail/IssueMainSection';
import Sidebar from '@/features/issue/components/Sidebar';
import VerticalStack from '@/layouts/VerticalStack';

export default function IssueDetailPage() {
  const { id } = useParams();
  const issueId = Number(id);
  const navigate = useNavigate();

  const {
    data: issueDetailData,
    isLoading: isIssueDetailLoading,
    isError: isIssueDetailError,
  } = useIssueDetail(issueId);

  const {
    data: commentData,
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
  if (isIssueDetailLoading) return <div>로딩 중...</div>;
  if (isIssueDetailError) return <div>에러 발생</div>;
  if (!issueDetailData) return;

  return (
    <VerticalStack>
      <IssueHeader
        {...issueDetailData}
        issueNumber={issueDetailData.id}
        commentCount={commentData?.length ?? 0}
      />
      <Divider />
      <MainArea>
        <IssueMainSection
          comments={commentData ?? []}
          isLoading={isCommentLoading}
          isError={isCommentError}
        />
        <Sidebar />
      </MainArea>
    </VerticalStack>
  );
}

const MainArea = styled.div`
  display: flex;
  gap: 32px;
`;
