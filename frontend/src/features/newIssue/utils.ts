import { type NewIssueState } from '@/features/newIssue/types';

export function buildIssuePayload(form: NewIssueState) {
  const payload: Record<string, unknown> = {
    title: form.title.trim(),
  };

  if (form.content !== '') {
    payload.content = form.content;
  }

  if (form.milestoneId !== null) {
    payload.milestoneId = form.milestoneId;
  }

  if (form.labelIds.length > 0) {
    payload.labelIds = form.labelIds;
  }

  if (form.assigneeIds.length > 0) {
    payload.assigneeIds = form.assigneeIds;
  }

  return payload;
}
