import { useQuery } from '@tanstack/react-query';
import getIssueDetail from '../api/getIssueDetail';

export default function useIssueDetail(issueId: number) {
  const { data, ...rest } = useQuery({
    queryKey: ['issueDetail', issueId],
    queryFn: () => getIssueDetail(issueId),
    enabled: !!issueId,
    retry: false,
  });

  return {
    issueDetail: data ?? null,
    ...rest,
  };
}
