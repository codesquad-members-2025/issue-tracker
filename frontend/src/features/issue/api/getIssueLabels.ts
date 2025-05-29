import { API } from '@/shared/constants/api';
import { type Label } from '@/features/label/types';

interface GetIssueLabelsResponse {
  labels: Label[];
}

export async function getIssueLabels(
  issueId: number,
): Promise<GetIssueLabelsResponse> {
  const res = await fetch(API.ISSUE_LABELS(issueId), {
    method: 'GET',
    // credentials: 'include', // 필요 시 주석 해제
  });

  const statusCodeHandler: Record<
    number,
    () => Promise<GetIssueLabelsResponse>
  > = {
    200: async () => await res.json(),
    403: () => {
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
