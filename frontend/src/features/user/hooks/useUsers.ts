import { useQuery } from '@tanstack/react-query';
import getUsers from '../api/getUsers';
import { type GetUsersResponse } from '../types';

export function useUsers() {
  const { data, isLoading, isError, refetch } = useQuery<GetUsersResponse>({
    queryKey: ['users'],
    queryFn: getUsers,
    staleTime: 1000 * 60 * 1,
  });

  return {
    users: data?.users ?? [],
    isLoading,
    isError,
    refetch,
  };
}
