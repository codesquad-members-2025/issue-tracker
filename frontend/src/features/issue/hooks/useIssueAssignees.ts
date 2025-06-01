import { useQuery } from '@tanstack/react-query';
import { getIssueAssignees } from '@/features/issue/api/getIssueAssignees';
import { type User } from '@/features/user/types';

export default function useIssueAssignees(issueId: number) {
  const { data, ...rest } = useQuery<{ assignees: User[] }>({
    queryKey: ['issue', issueId, 'assignees'],
    queryFn: () => getIssueAssignees(issueId),
  });

  return {
    issueAssignees: data?.assignees ?? [],
    ...rest,
  };
}
