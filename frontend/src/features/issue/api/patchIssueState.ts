import { API } from '@/shared/constants/api';

interface PatchIssueStateParams {
  issueId: number;
  targetClosed: boolean;
}

export default async function patchIssueState({
  issueId,
  targetClosed,
}: PatchIssueStateParams): Promise<void> {
  const response = await fetch(API.ISSUE_STATE(issueId), {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    // credentials: 'include',
    body: JSON.stringify({ targetClosed }),
  });

  const statusCodeHandler: Record<number, () => Promise<void>> = {
    204: () => Promise.resolve(),
    401: () => {
      window.location.href = '/login';
      return Promise.reject(new Error('로그인이 필요합니다'));
    },
    404: () => Promise.reject(new Error('요청 경로가 잘못되었습니다')),
    500: () => Promise.reject(new Error('서버 오류가 발생했습니다')),
  };

  const defaultHandler = () =>
    Promise.reject(
      new Error(`예상치 못한 오류가 발생했습니다 (status: ${response.status})`),
    );

  return (statusCodeHandler[response.status] ?? defaultHandler)();
}
