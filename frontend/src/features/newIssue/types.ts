export interface NewIssueState {
  title: string;
  content: string;
  milestone: number | null;
  labels: number[];
  assignees: number[];
}
