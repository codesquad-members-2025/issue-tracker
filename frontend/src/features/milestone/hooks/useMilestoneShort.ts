import { useQuery } from '@tanstack/react-query';
import getMilestonesShort from '../api/getMilestonesShort';
import { type GetMilestonesShortResponse } from '../types';

export default function useMilestonesShort() {
  const { data, isLoading, isError, refetch } =
    useQuery<GetMilestonesShortResponse>({
      queryKey: ['milestonesShort'],
      queryFn: getMilestonesShort,
      staleTime: 1000 * 60 * 1,
    });

  return {
    milestones: data?.milestones ?? [],
    isLoading,
    isError,
    refetch,
  };
}
