import { API } from '@/shared/constants/api';
import { type IssueDetailResponse } from '../types/issue';

export default async function getIssueDetail(
  issueId: number,
): Promise<IssueDetailResponse> {
  const res = await fetch(`${API.ISSUES}/${issueId}`);

  const statusCodeHandler: Record<number, () => Promise<IssueDetailResponse>> =
    {
      200: async () => await res.json(),
      401: () => Promise.reject(new Error('로그인이 필요합니다')),
      404: () => Promise.reject(new Error('요청한 이슈가 존재하지 않습니다')),
      500: () => Promise.reject(new Error('서버 오류가 발생했습니다')),
    };

  const defaultHandler = () =>
    Promise.reject(
      new Error(`예상치 못한 오류가 발생했습니다 (status: ${res.status})`),
    );

  return (statusCodeHandler[res.status] ?? defaultHandler)();
}
