import { useReducer } from 'react';
import { newIssueFormReducer } from '@/features/newIssue/reducers/newIssueFormReducer';
import { type NewIssueState } from '@/features/newIssue/types';

export const initialNewIssueFormState: NewIssueState = {
  title: '',
  content: '',
  milestone: null,
  labels: [],
  assignees: [],
};

export function useNewIssueForm() {
  const [state, dispatch] = useReducer(
    newIssueFormReducer,
    initialNewIssueFormState,
  );

  return { state, dispatch };
}
