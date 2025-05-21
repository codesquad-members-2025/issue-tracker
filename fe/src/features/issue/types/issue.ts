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

export interface IssuesResponse {
  issues: Issue[];
  openCount: number;
  closeCount: number;
}

export interface Milestone {
  id: number;
  name: string;
  progressRate: number;
}

export interface Assignee {
  id: number;
  nickname: string;
  profileImage: string;
}

export interface IssueDetailResponse {
  id: number;
  author: {
    id: number;
    nickname: string;
    profileImage: string;
  };
  title: string;
  content: string;
  labels: Label[];
  milestone: Milestone | null;
  assignees: Assignee[];
  isClosed: boolean;
  createdAt: string;
  updatedAt: string | null;
}

export interface CommentAuthor {
  id: number;
  nickname: string;
  profileImage: string;
}

export interface Comment {
  id: number;
  author: CommentAuthor;
  content: string;
  createdAt: string;
  updatedAt: string | null;
}

export interface CommentsResponse {
  comments: Comment[];
}
