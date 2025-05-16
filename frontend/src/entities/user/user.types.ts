// API 레이어가 반환하는 raw 타입
export interface UserApiDto {
	id: number;
	username: string;
	imageUrl: string;
}

// API wrapper 전체 응답
export interface UsersResponseDto {
	success: boolean;
	data: {
		total: number;
		page: number;
		perPage: number;
		users: UserApiDto[];
	};
	error: string | null;
}

// 프론트엔드에서 사용할 User 타입
export interface User {
	id: number;
	username: string;
	avatarUrl: string; // DTO의 imageUrl을 매핑
}
