import { useState } from 'react';
import VerticalStack from '@/layouts/VerticalStack';
import Divider from '@/shared/components/Divider';
import NewIssueActionButtons from '@/features/newIssue/components/NewIssueActionButtons';
import NewIssueHeader from '@/features/newIssue/components/NewIssueHeader';
import NewIssueFormSection from '@/features/newIssue/components/NewIssueFormSection';

export default function NewIssuePage() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  function isTitleEmpty(title: string) {
    return title.trim() === '';
  }

  function handleCreateIssue() {
    //TODO 이슈 생성 로직 추가
  }

  return (
    <VerticalStack>
      <NewIssueHeader />
      <Divider />
      <NewIssueFormSection
        title={title}
        onTitleChange={setTitle}
        content={content}
        onContentChange={setContent}
      />
      <Divider />
      <NewIssueActionButtons
        isSubmitDisabled={isTitleEmpty(title)}
        onSubmit={handleCreateIssue}
      />
    </VerticalStack>
  );
}
