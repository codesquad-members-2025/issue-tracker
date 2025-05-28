import { initialNewIssueFormState } from '../hooks/useNewIssueForm';

export type NewIssueState = {
  title: string;
  content: string;
  milestoneId: number | null;
  labelIds: number[];
  assigneeIds: number[];
};

export type Action =
  | { type: 'SET_TITLE'; payload: string }
  | { type: 'SET_CONTENT'; payload: string }
  | { type: 'SET_MILESTONE'; payload: number | null }
  | { type: 'TOGGLE_LABEL'; payload: number }
  | { type: 'TOGGLE_ASSIGNEE'; payload: number }
  | { type: 'RESET_FORM' };

export function newIssueFormReducer(
  state: NewIssueState,
  action: Action,
): NewIssueState {
  switch (action.type) {
    case 'SET_TITLE':
      return { ...state, title: action.payload };
    case 'SET_CONTENT':
      return { ...state, content: action.payload };
    case 'SET_MILESTONE':
      return { ...state, milestoneId: action.payload };
    case 'TOGGLE_LABEL':
      return {
        ...state,
        labelIds: state.labelIds.includes(action.payload)
          ? state.labelIds.filter(id => id !== action.payload)
          : [...state.labelIds, action.payload],
      };
    case 'TOGGLE_ASSIGNEE':
      return {
        ...state,
        assigneeIds: state.assigneeIds.includes(action.payload)
          ? state.assigneeIds.filter(id => id !== action.payload)
          : [...state.assigneeIds, action.payload],
      };

    case 'RESET_FORM':
      return initialNewIssueFormState;
    default:
      return state;
  }
}
