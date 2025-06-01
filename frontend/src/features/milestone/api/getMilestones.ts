import { API } from '@/shared/constants/api';
import { type GetMilestonesResponse } from '@/features/milestone/types';

export default async function getMilestones(): Promise<GetMilestonesResponse> {
  const res = await fetch(`${API.MILESTONES}`);

  const statusCodeHandler: Record<
    number,
    () => Promise<GetMilestonesResponse>
  > = {
    200: async () => await res.json(),
    401: () => Promise.reject(new Error('로그인이 필요합니다')),
    404: () => Promise.reject(new Error('요청 경로가 잘못되었습니다')),
    500: () => Promise.reject(new Error('서버 오류가 발생했습니다')),
  };

  const defaultHandler = () =>
    Promise.reject(
      new Error(`예상치 못한 오류가 발생했습니다 (status: ${res.status})`),
    );

  return (statusCodeHandler[res.status] ?? defaultHandler)();
}
