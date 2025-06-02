export interface User {
  id: number;
  nickname: string;
  profileImage: string;
}

export interface GetUsersResponse {
  users: User[];
}
