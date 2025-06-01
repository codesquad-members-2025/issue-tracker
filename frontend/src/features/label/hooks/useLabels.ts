import { useQuery } from '@tanstack/react-query';
import getLabels from '@/features/label/api/getLabels';
import { type GetLabelsResponse } from '@/features/label/types';

export function useLabels() {
  const { data, isLoading, isError, refetch } = useQuery<GetLabelsResponse>({
    queryKey: ['labels'],
    queryFn: getLabels,
  });

  return {
    labels: data?.labels ?? [],
    isLoading,
    isError,
    refetch,
  };
}
