import { API } from '@/shared/constants/api';
import { type CommentsResponse } from '../types/issue';

export default async function getIssueComments(
  issueId: number,
): Promise<CommentsResponse> {
  const res = await fetch(`${API.ISSUES}/${issueId}/comments`);

  if (res.status === 200) return res.json();

  const errorMessages: Record<number, string> = {
    401: '로그인이 필요합니다',
    404: '요청한 이슈가 존재하지 않습니다',
    500: '서버 오류가 발생했습니다',
  };

  const message = errorMessages[res.status];
  throw new Error(
    message ?? `예상치 못한 오류가 발생했습니다 (status: ${res.status})`,
  );
}
