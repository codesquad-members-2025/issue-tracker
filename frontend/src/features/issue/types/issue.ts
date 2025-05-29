import type { Label } from '@/features/label/types';
import type { User } from '@/features/user/types';
import type { MilestoneDetail } from '@/features/milestone/types';

export type IssueStatus = 'open' | 'closed';

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

export interface Assignee {
  id: number;
  nickname: string;
  profileImage: string;
}

export interface IssueDetailResponse {
  id: number;
  author: User;
  title: string;
  content: string | null;
  labels: Label[];
  milestone: MilestoneDetail | null;
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
