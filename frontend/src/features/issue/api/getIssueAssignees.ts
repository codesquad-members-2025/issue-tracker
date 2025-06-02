import { type User } from '@/features/user/types';
import { API } from '@/shared/constants/api';

export interface GetIssueAssigneesResponse {
  assignees: User[];
}

export async function getIssueAssignees(
  issueId: number,
): Promise<GetIssueAssigneesResponse> {
  const res = await fetch(API.ISSUE_ASSIGNEES(issueId), {
    method: 'GET',
    // credentials: 'include',
  });

  const statusCodeHandler: Record<
    number,
    () => Promise<GetIssueAssigneesResponse>
  > = {
    200: async () => await res.json(),
    401: () => {
      window.location.href = '/login';
      return Promise.reject(new Error('로그인이 필요합니다'));
    },
    404: () => Promise.reject(new Error('요청한 이슈가 존재하지 않습니다')),
    500: () => Promise.reject(new Error('서버 오류가 발생했습니다')),
  };

  const defaultHandler = () =>
    Promise.reject(
      new Error(`예상치 못한 오류가 발생했습니다 (status: ${res.status})`),
    );

  return (statusCodeHandler[res.status] ?? defaultHandler)();
}
