import { useNewIssueForm } from '@/features/newIssue/hooks/useNewIssueForm';
import VerticalStack from '@/layouts/VerticalStack';
import Divider from '@/shared/components/Divider';
import NewIssueActionButtons from '@/features/newIssue/components/NewIssueActionButtons';
import NewIssueHeader from '@/features/newIssue/components/NewIssueHeader';
import NewIssueFormSection from '@/features/newIssue/components/NewIssueFormSection';

export default function NewIssuePage() {
  const { state: issueForm, dispatch: updateIssueForm } = useNewIssueForm();

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
      />
    </VerticalStack>
  );
}
