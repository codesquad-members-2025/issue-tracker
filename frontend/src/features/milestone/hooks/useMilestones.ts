import { useQuery } from '@tanstack/react-query';
import getMilestones from '@/features/milestone/api/getMilestones';
import { type GetMilestonesResponse } from '@/features/milestone/types';

export default function useMilestones() {
  const { data, isLoading, isError, refetch } = useQuery<GetMilestonesResponse>(
    {
      queryKey: ['milestones'],
      queryFn: getMilestones,
      staleTime: 1000 * 60 * 1,
    },
  );

  return {
    milestones: data?.milestones ?? [],
    isLoading,
    isError,
    refetch,
  };
}
