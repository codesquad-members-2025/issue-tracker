import { useState } from 'react';
import { type IssueStatus } from '@/features/issue/types/issue';
import IssueListContainer from '@/features/issue/components/list/IssueListContainer';

export default function IssueListPage() {
  const [selectedTab, setSelectedTab] = useState<IssueStatus>('open');

  return (
    <>
      {/* TODO IssueAdvancedFilter 컴포넌트 구현  */}
      <IssueListContainer
        onChangeTab={status => setSelectedTab(status)}
        selected={selectedTab}
      />
    </>
  );
}
