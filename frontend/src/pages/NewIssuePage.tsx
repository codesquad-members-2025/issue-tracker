import { useNavigate } from 'react-router-dom';
import { useNewIssueForm } from '@/features/newIssue/hooks/useNewIssueForm';
import { useCreateIssue } from '@/features/newIssue/hooks/useCreateIssue';
import { buildIssuePayload } from '@/features/newIssue/utils';
import VerticalStack from '@/layouts/VerticalStack';
import Divider from '@/shared/components/Divider';
import NewIssueActionButtons from '@/features/newIssue/components/NewIssueActionButtons';
import NewIssueHeader from '@/features/newIssue/components/NewIssueHeader';
import NewIssueFormSection from '@/features/newIssue/components/NewIssueFormSection';

export default function NewIssuePage() {
  const navigate = useNavigate();
  const { state: issueForm, dispatch: updateIssueForm } = useNewIssueForm();
  const { mutate: postNewIssue } = useCreateIssue();

  function handleCancel() {
    updateIssueForm({ type: 'RESET_FORM' });
    navigate('/');
  }

  function isTitleEmpty(title: string) {
    return title.trim() === '';
  }

  function handleCreateIssue() {
    const payload = buildIssuePayload(issueForm);
    console.log('[POST] /api/v1/issues payload:', payload);
    postNewIssue(payload);
  }

  function handleTitleChange(val: string) {
    updateIssueForm({ type: 'SET_TITLE', payload: val });
  }

  function handleContentChange(val: string) {
    updateIssueForm({ type: 'SET_CONTENT', payload: val });
  }

  function handleMilestoneChange(id: number) {
    updateIssueForm({
      type: 'SET_MILESTONE',
      payload: issueForm.milestone === id ? null : id,
    });
  }

  function handleToggleLabel(id: number) {
    updateIssueForm({ type: 'TOGGLE_LABEL', payload: id });
  }

  function handleToggleAssignee(id: number) {
    updateIssueForm({ type: 'TOGGLE_ASSIGNEE', payload: id });
  }

  return (
    <VerticalStack>
      <NewIssueHeader />
      <Divider />
      <NewIssueFormSection
        title={issueForm.title}
        onTitleChange={handleTitleChange}
        content={issueForm.content}
        onContentChange={handleContentChange}
        milestoneId={issueForm.milestone}
        onMilestoneChange={handleMilestoneChange}
        selectedLabelIds={issueForm.labels}
        onToggleLabel={handleToggleLabel}
        selectedAssigneeIds={issueForm.assignees}
        onToggleAssignee={handleToggleAssignee}
      />
      <Divider />
      <NewIssueActionButtons
        isSubmitDisabled={isTitleEmpty(issueForm.title)}
        onSubmit={handleCreateIssue}
        onCancel={handleCancel}
      />
    </VerticalStack>
  );
}
