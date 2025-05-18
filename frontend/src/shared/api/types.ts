export interface ApiResponse<T, E = unknown> {
	success: boolean;
	data: T;
	error: E | null;
}
