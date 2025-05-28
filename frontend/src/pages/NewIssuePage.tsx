import { useNavigate } from 'react-router-dom';
import { useNewIssueForm } from '@/features/newIssue/hooks/useNewIssueForm';
import { buildIssuePayload } from '@/features/newIssue/utils';
import VerticalStack from '@/layouts/VerticalStack';
import Divider from '@/shared/components/Divider';
import NewIssueActionButtons from '@/features/newIssue/components/NewIssueActionButtons';
import NewIssueHeader from '@/features/newIssue/components/NewIssueHeader';
import NewIssueFormSection from '@/features/newIssue/components/NewIssueFormSection';

export default function NewIssuePage() {
  const navigate = useNavigate();
  const { state: issueForm, dispatch: updateIssueForm } = useNewIssueForm();

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

    // TODO fetch/postIssue(payload)
  }

  return (
    <VerticalStack>
      <NewIssueHeader />
      <Divider />
      <NewIssueFormSection
        title={issueForm.title}
        onTitleChange={val =>
          updateIssueForm({ type: 'SET_TITLE', payload: val })
        }
        content={issueForm.content}
        onContentChange={val =>
          updateIssueForm({ type: 'SET_CONTENT', payload: val })
        }
        milestoneId={issueForm.milestoneId}
        onMilestoneChange={id =>
          updateIssueForm({ type: 'SET_MILESTONE', payload: id })
        }
        selectedLabelIds={issueForm.labelIds}
        onToggleLabel={id =>
          updateIssueForm({ type: 'TOGGLE_LABEL', payload: id })
        }
        selectedAssigneeIds={issueForm.assigneeIds}
        onToggleAssignee={id =>
          updateIssueForm({ type: 'TOGGLE_ASSIGNEE', payload: id })
        }
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
