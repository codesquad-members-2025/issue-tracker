import { API } from '@/shared/constants/api';
import type {
  NewIssuePayload,
  PostNewIssueResponse,
} from '@/features/newIssue/types';

export const postNewIssue = async (
  payload: NewIssuePayload,
): Promise<PostNewIssueResponse> => {
  const response = await fetch(`${API.POST_ISSUE}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(payload),
  });

  const statusCodeHandler: Record<number, () => Promise<unknown>> = {
    201: async () => await response.json(),
    401: () => Promise.reject(new Error('로그인이 필요합니다')),
    404: () => Promise.reject(new Error('요청 경로가 잘못되었습니다')),
    500: () => Promise.reject(new Error('서버 오류가 발생했습니다')),
  };

  const defaultHandler = () =>
    Promise.reject(
      new Error(`예상치 못한 오류가 발생했습니다 (status: ${response.status})`),
    );

  const data = await (statusCodeHandler[response.status] ?? defaultHandler)();

  return data as PostNewIssueResponse;
};
