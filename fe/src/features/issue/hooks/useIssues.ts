import { useQuery } from '@tanstack/react-query';
import { getIssues } from '../api/getIssues';

export default function useIssues(filterQuery: string) {
  const { data, ...rest } = useQuery({
    queryKey: ['issues', filterQuery],
    queryFn: () => getIssues(filterQuery),
  });

  return {
    issueList: data?.issues ?? [],
    openCount: data?.openCount ?? 0,
    closeCount: data?.closeCount ?? 0,
    ...rest,
  };
}
