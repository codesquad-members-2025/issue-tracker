import { useQuery } from '@tanstack/react-query';
import getLabels from '../api/getLabels';
import { type GetLabelsResponse } from '../types';

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
