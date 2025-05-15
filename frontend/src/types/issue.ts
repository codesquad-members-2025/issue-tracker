export interface Issue {
  id: number;
  number: number;
  title: string;
  createdBy: string;
  createdAt: string;
  avatarUrl: string;
  labels?: string[];
  milestone?: string;
  state: "open" | "closed";
}
