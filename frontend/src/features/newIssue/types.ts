export interface NewIssueState {
  title: string;
  content: string;
  milestone: number | null;
  labels: number[];
  assignees: number[];
}

export interface NewIssuePayload {
  title: string;
  content?: string;
  assignees?: number[];
  labels?: number[];
  milestone?: number | null;
}

export interface PostNewIssueResponse {
  id: number;
}
