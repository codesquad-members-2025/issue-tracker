import { useQuery } from '@tanstack/react-query';
import getIssueDetail from '../api/getIssueDetail';

export default function useIssueDetail(issueId: number) {
  return useQuery({
    queryKey: ['issueDetail', issueId],
    queryFn: () => getIssueDetail(issueId),
    enabled: !!issueId,
  });
}
