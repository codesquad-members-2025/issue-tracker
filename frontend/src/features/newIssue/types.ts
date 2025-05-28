export interface NewIssueState {
  title: string;
  content: string | null;
  milestoneId: number | null;
  labelIds: number[];
  assigneeIds: number[];
}
