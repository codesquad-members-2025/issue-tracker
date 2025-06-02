// src/features/issue/hooks/useIssueLabels.ts
import { useQuery } from '@tanstack/react-query';
import { getIssueLabels } from '@/features/issue/api/getIssueLabels';
import { type Label } from '@/features/label/types';

export default function useIssueLabels(issueId: number) {
  const { data, ...rest } = useQuery<{ labels: Label[] }>({
    queryKey: ['issue', issueId, 'labels'],
    queryFn: () => getIssueLabels(issueId),
  });

  return {
    issueLabels: data?.labels ?? [],
    ...rest,
  };
}
