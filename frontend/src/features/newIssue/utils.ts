import {
  type NewIssuePayload,
  type NewIssueState,
} from '@/features/newIssue/types';

export function buildIssuePayload(form: NewIssueState): NewIssuePayload {
  const payload: NewIssuePayload = {
    title: form.title.trim(),
  };

  if (form.content !== '') {
    payload.content = form.content;
  }

  if (form.milestone !== null) {
    payload.milestone = form.milestone;
  }

  if (form.labels.length > 0) {
    payload.labels = form.labels;
  }

  if (form.assignees.length > 0) {
    payload.assignees = form.assignees;
  }

  return payload;
}
