import { useQuery } from '@tanstack/react-query';
import fetchIssueComments from '@/features/issue/api/getIssueComments';
import { type CommentsResponse } from '@/features/issue/types/issue';

export default function useIssueComments(issueId: number) {
  const { data, ...rest } = useQuery<CommentsResponse>({
    queryKey: ['issueComments', issueId],
    queryFn: () => fetchIssueComments(issueId),
    enabled: !!issueId,
    retry: false,
  });

  return {
    data: data?.comments,
    ...rest,
  };
}
