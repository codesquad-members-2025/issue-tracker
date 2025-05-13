export type IssueStatus = 'open' | 'closed';

export interface Label {
  id: number;
  name: string;
  color: string;
  description: string | null;
}

export interface Issue {
  id: number;
  author: string;
  title: string;
  labels: Label[];
  milestone: string | null;
  isClosed: boolean;
  createdAt: string;
  updatedAt: string | null;
  assigneesProfileImages: string[];
}
