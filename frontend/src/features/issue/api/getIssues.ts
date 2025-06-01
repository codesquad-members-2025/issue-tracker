import { API } from '@/shared/constants/api';
import { type IssuesResponse } from '@/features/issue/types/issue';

export async function getIssues(filterQuery: string): Promise<IssuesResponse> {
  const url = new URL(API.ISSUES, window.location.origin);
  url.searchParams.set('q', filterQuery);

  const res = await fetch(url.toString(), {
    credentials: 'include',
  });

  const statusCodeHandler: Record<number, () => Promise<IssuesResponse>> = {
    200: async () => await res.json(),
    400: () =>
      Promise.reject(new Error('잘못된 요청입니다 (query string 오류)')),
    401: () => Promise.reject(new Error('로그인이 필요합니다')),
    404: () => Promise.reject(new Error('요청한 경로가 존재하지 않습니다')),
    500: () => Promise.reject(new Error('서버 오류가 발생했습니다')),
  };

  const defaultHandler = () =>
    Promise.reject(new Error(`예상치 못한 오류 (status: ${res.status})`));

  return (statusCodeHandler[res.status] ?? defaultHandler)();
}
