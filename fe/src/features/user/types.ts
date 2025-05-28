export interface User {
  id: string;
  nickname: string;
  profileImage: string | null;
}

export interface GetUsersResponse {
  users: User[];
}
