// src/shared/api/mockData.ts

/**
 * 경로별 mock 데이터를 반환하는 유틸
 */
export type MockData = unknown;

// 경로에 따른 mock 데이터 로더 매핑
const mockLoaders: Record<string, () => Promise<MockData>> = {
	'/api/issues': async () => {
		const { mockIssueListResponse } = await import(
			'@/entities/issue/issueFixtures'
		);
		return mockIssueListResponse;
	},
	'/api/users': async () => {
		const { mockUserListResponse } = await import(
			'@/entities/user/userFixtures'
		);
		return mockUserListResponse;
	},
};

/**
 * 주어진 경로에 매핑된 mock 데이터를 반환합니다.
 * @param path API 엔드포인트 경로
 * @throws 정의되지 않은 경로일 경우 Error
 */
export async function getMockData(path: string): Promise<MockData> {
	const loader = mockLoaders[path];
	if (!loader) {
		throw new Error(`알 수 없는 mock 경로: ${path}`);
	}
	return loader();
}
