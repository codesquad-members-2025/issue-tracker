import { useMutation, useQueryClient } from '@tanstack/react-query';
import patchIssueState from '@/features/issue/api/patchIssueState';
import { type IssueDetailResponse } from '@/features/issue/types/issue';

export default function usePatchIssueState(issueId: number) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: patchIssueState,
    onSuccess: () => {
      queryClient.setQueryData(
        ['issueDetail', issueId],
        (prev: IssueDetailResponse) => {
          if (!prev) return prev;
          return {
            ...prev,
            isClosed: !prev.isClosed,
          };
        },
      );
    },
  });
}
