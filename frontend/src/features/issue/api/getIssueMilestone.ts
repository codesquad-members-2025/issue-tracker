import { API } from '@/shared/constants/api';
import { type MilestoneDetail } from '@/features/milestone/types';

export async function getIssueMilestone(
  issueId: number,
): Promise<MilestoneDetail | null> {
  const res = await fetch(API.ISSUE_MILESTONE(issueId), {
    method: 'GET',
    credentials: 'include',
  });

  const statusCodeHandler: Record<
    number,
    () => Promise<MilestoneDetail | null>
  > = {
    200: async () => await res.json(),
    401: () => {
      window.location.href = '/login';
      return Promise.reject(new Error('로그인이 필요합니다'));
    },
    404: () => Promise.resolve(null), // 없을 수 있으므로 null 처리
    500: () => Promise.reject(new Error('서버 오류가 발생했습니다')),
  };

  const defaultHandler = () =>
    Promise.reject(
      new Error(`예상치 못한 오류가 발생했습니다 (status: ${res.status})`),
    );

  return (statusCodeHandler[res.status] ?? defaultHandler)();
}
