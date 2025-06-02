import { API } from '@/shared/constants/api';
import { type CommentsResponse } from '@/features/issue/types/issue';

export default async function getIssueComments(
  issueId: number,
): Promise<CommentsResponse> {
  const res = await fetch(`${API.ISSUES}/${issueId}/comments`);

  const statusCodeHandler: Record<number, () => Promise<CommentsResponse>> = {
    200: async () => await res.json(),
    401: () => Promise.reject(new Error('로그인이 필요합니다')),
    404: () => Promise.reject(new Error('요청한 댓글이 존재하지 않습니다')),
    500: () => Promise.reject(new Error('서버 오류가 발생했습니다')),
  };

  const defaultHandler = () =>
    Promise.reject(
      new Error(`예상치 못한 오류가 발생했습니다 (status: ${res.status})`),
    );

  return (statusCodeHandler[res.status] ?? defaultHandler)();
}
