import { useQuery } from '@tanstack/react-query';
import fetchIssueComments from '../api/getIssueComments';
import { type Comment } from '../types/issue';

export default function useIssueComments(issueId: number) {
  return useQuery<Comment[]>({
    queryKey: ['issueComments', issueId],
    queryFn: () => fetchIssueComments(issueId),
    enabled: !!issueId,
    retry: false,
  });
}
