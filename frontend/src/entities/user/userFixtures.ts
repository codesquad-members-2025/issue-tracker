// src/entities/user/userFixtures.ts
import type { ApiResponse } from '@/shared/api/types';
import type { UserApiDto } from './user.types';
import type { UserListData } from './userApi';

// Mock user data
const users: UserApiDto[] = [
	{
		id: 1,
		username: 'alice',
		imageUrl: 'https://example.com/alice.png',
	},
	{
		id: 2,
		username: 'bob',
		imageUrl: 'https://example.com/bob.png',
	},
];

// Mock API response for user list
export const mockUserListResponse: ApiResponse<UserListData> = {
	success: true,
	data: {
		total: users.length,
		page: 0,
		perPage: users.length,
		users: users,
	},
	error: null,
};
