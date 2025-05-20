import styled from '@emotion/styled';
import Divider from '@/shared/components/Divider';
import IssueHeader from '@/features/issue/components/detail/IssueHeader';
import IssueMainSection from '@/features/issue/components/detail/IssueMainSection';
import Sidebar from '@/features/issue/components/Sidebar';
import VerticalStack from '@/layouts/VerticalStack';

export default function IssueDetailPage() {
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
