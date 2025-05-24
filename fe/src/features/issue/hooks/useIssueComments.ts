import { useQuery } from '@tanstack/react-query';
import fetchIssueComments from '../api/getIssueComments';
import { type CommentsResponse } from '../types/issue';

export default function useIssueComments(issueId: number) {
  const { data, ...rest } = useQuery<CommentsResponse>({
    queryKey: ['issueComments', issueId],
    queryFn: () => fetchIssueComments(issueId),
    enabled: !!issueId,
    retry: false,
  });

  return {
    commentList: data?.comments ?? [],
    ...rest,
  };
}
