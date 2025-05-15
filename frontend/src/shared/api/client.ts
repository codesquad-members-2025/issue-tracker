const BASE_URL = import.meta.env.VITE_API_BASE_URL ?? '/api'; // 예: "https://api.example.com"
const USE_MOCK = import.meta.env.VITE_USE_MOCK === 'true'; // .env에서 VITE_USE_MOCK=true로 설정 시 모킹 활성화

/**
 * 실제 서버와 통신하는 getJSON 함수
 */
async function realGetJSON<T>(path: string): Promise<T> {
	const res = await fetch(`${BASE_URL}${path}`, {
		method: 'GET',
		headers: { Accept: 'application/json' },
	});

	/**
	 * 응답 상태가 200~299가 아닐 경우 Error를 던집니다.
	 * 호출자는 이 에러를 catch하여 토스트 메시지 표시, 재시도 로직 실행,
	 * 에러 페이지 이동 등 적절한 처리를 수행해야 합니다.
	 */
	if (!res.ok) {
		throw new Error(`API 요청 실패: ${res.status} ${res.statusText}`);
	}

	// 성공 응답일 경우 JSON 본문을 파싱하여 반환합니다.
	return res.json();
}

/**
 * 모의 데이터를 반환하는 getJSON 함수
 * path에 따라 필요한 mock 데이터를 분기하여 반환하세요.
 */
async function mockGetJSON<T>(path: string): Promise<T> {
	// 예시: '/api/issues' 요청 시 issueFixtures에서 정의한 mock 데이터를 반환
	if (path === '/issues') {
		const { mockIssueListResponse } = await import(
			'@/entities/issue/issueFixtures'
		);
		// 타입 단언을 통해 T 형태로 반환
		return mockIssueListResponse as unknown as T;
	}

	// 정의되지 않은 경로는 에러 처리
	throw new Error(`알 수 없는 mock 경로: ${path}`);
}

/**
 * 주어진 경로(path)에 GET 요청을 보내고, JSON 응답을 파싱하여 반환합니다.
 *
 * @template T - 반환될 JSON 데이터의 타입을 지정합니다.
 * @param path - API 엔드포인트 경로 (예: "/api/issues").
 * @returns 파싱된 JSON 데이터(T 타입)의 Promise.
 *
 * @throws Error if HTTP 상태 코드가 200~299 범위가 아닐 경우 Error를 던집니다.
 *
 * 실제 통신 또는 mock 중 하나를 자동으로 선택하여 사용합니다.
 */
export const getJSON = USE_MOCK ? mockGetJSON : realGetJSON;
