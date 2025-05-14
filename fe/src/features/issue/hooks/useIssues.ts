import { useQuery } from '@tanstack/react-query';
import { getIssues } from '../api/getIssues';

export default function useIssues(filterQuery: string) {
  return useQuery({
    queryKey: ['issues', filterQuery],
    queryFn: () => getIssues(filterQuery),
  });
}
