// src/entities/user/api/userApi.ts
import { getJSON } from '@/shared/api/client';
import type { ApiResponse } from '@/shared/api/types';
import type { UserApiDto, UsersResponseDto } from './user.types';

/** API에서 반환되는 data 필드 타입 */
export type UserListData = UsersResponseDto['data'];

/**
 * 전체 유저 목록을 가져옵니다.
 * - page, perPage 같은 파라미터가 필요하다면 URL에 쿼리스트링을 추가해 주세요.
 */
export async function fetchUsers(): Promise<UserListData> {
	const res = await getJSON<ApiResponse<UserListData>>('/api/users');
	return res.data;
}

/**
 * 단일 유저를 ID로 조회합니다.
 */
export async function fetchUserById(id: number): Promise<UserApiDto> {
	const res = await getJSON<ApiResponse<UserApiDto>>(`/api/users/${id}`);
	return res.data;
}
