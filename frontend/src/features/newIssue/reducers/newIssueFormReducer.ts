import { initialNewIssueFormState } from '@/features/newIssue/hooks/useNewIssueForm';
import { type NewIssueState } from '@/features/newIssue/types';

export type Action =
  | { type: 'SET_TITLE'; payload: string }
  | { type: 'SET_CONTENT'; payload: string | ((prev: string) => string) }
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
    case 'SET_CONTENT': {
      const nextContent =
        typeof action.payload === 'function'
          ? action.payload(state.content)
          : action.payload;
      return { ...state, content: nextContent };
    }
    case 'SET_MILESTONE':
      return { ...state, milestone: action.payload };
    case 'TOGGLE_LABEL':
      return {
        ...state,
        labels: state.labels.includes(action.payload)
          ? state.labels.filter(id => id !== action.payload)
          : [...state.labels, action.payload],
      };
    case 'TOGGLE_ASSIGNEE':
      return {
        ...state,
        assignees: state.assignees.includes(action.payload)
          ? state.assignees.filter(id => id !== action.payload)
          : [...state.assignees, action.payload],
      };

    case 'RESET_FORM':
      return initialNewIssueFormState;
    default:
      return state;
  }
}
