import { useMutation, useQueryClient } from '@tanstack/react-query';
import postComment from '@/features/issue/api/postComment';

export default function useCreateComment(issueId: number) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (content: string) => postComment(issueId, content),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['issueComments', issueId] });
    },
  });
}
