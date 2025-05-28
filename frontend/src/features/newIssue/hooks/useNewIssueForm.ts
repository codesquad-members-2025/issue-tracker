import { useReducer } from 'react';
import {
  newIssueFormReducer,
  type NewIssueState,
} from '../reducers/newIssueFormReducer';

export const initialNewIssueFormState: NewIssueState = {
  title: '',
  content: '',
  milestoneId: null,
  labelIds: [],
  assigneeIds: [],
};

export function useNewIssueForm() {
  const [state, dispatch] = useReducer(
    newIssueFormReducer,
    initialNewIssueFormState,
  );

  return { state, dispatch };
}
