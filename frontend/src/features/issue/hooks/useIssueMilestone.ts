import { useQuery } from '@tanstack/react-query';
import { getIssueMilestone } from '@/features/issue/api/getIssueMilestone';
import { type MilestoneDetail } from '@/features/milestone/types';

export default function useIssueMilestone(issueId: number) {
  const { data, ...rest } = useQuery<MilestoneDetail | null>({
    queryKey: ['issue', issueId, 'milestone'],
    queryFn: () => getIssueMilestone(issueId),
  });

  return {
    issueMilestone: data ?? null,
    ...rest,
  };
}
