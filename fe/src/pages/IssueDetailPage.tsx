import styled from '@emotion/styled';
import { useParams } from 'react-router-dom';
import useIssueDetail from '@/features/issue/hooks/useIssueDetail';
import Divider from '@/shared/components/Divider';
import IssueHeader from '@/features/issue/components/detail/IssueHeader';
import IssueMainSection from '@/features/issue/components/detail/IssueMainSection';
import Sidebar from '@/features/issue/components/Sidebar';
import VerticalStack from '@/layouts/VerticalStack';

export default function IssueDetailPage() {
  const { id } = useParams();
  const issueId = Number(id);

  const {
    data: issueDetailData,
    isLoading: isIssueDetailLoading,
    error: IssueDetailError,
  } = useIssueDetail(issueId);

  // TODO 로딩,에러 상태에 따라 분기처리 내부적으로 처리
  if (isIssueDetailLoading) return <div>로딩 중...</div>;
  if (IssueDetailError) return <div>에러 발생</div>;

  return (
    <VerticalStack>
      <IssueHeader />
      <Divider />
      <MainArea>
        <IssueMainSection />
        <Sidebar />
      </MainArea>
    </VerticalStack>
  );
}

const MainArea = styled.div`
  display: flex;
  gap: 32px;
`;
