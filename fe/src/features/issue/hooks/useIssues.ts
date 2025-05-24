import { useQuery, keepPreviousData } from '@tanstack/react-query';
import { getIssues } from '../api/getIssues';

export default function useIssues(filterQuery: string) {
  const { data, ...rest } = useQuery({
    queryKey: ['issues', filterQuery],
    queryFn: () => getIssues(filterQuery),
    placeholderData: keepPreviousData,
  });

  return {
    issueList: data?.issues,
    openCount: data?.openCount,
    closeCount: data?.closeCount,
    ...rest,
  };
}
