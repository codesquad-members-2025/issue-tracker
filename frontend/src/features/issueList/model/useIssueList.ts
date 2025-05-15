import type { IssueListData } from '@/entities/issue/issue';
import { fetchIssues } from '@/entities/issue/issueAPI';
import { useEffect, useState } from 'react';

export function useIssueList() {
	const [data, setData] = useState<IssueListData | null>(null);
	const [isLoading, setIsLoading] = useState<boolean>(true);
	const [error, setError] = useState<Error | null>(null);

	useEffect(() => {
		let mounted = true;

		async function load() {
			setIsLoading(true);
			try {
				const result = await fetchIssues();
				if (mounted) {
					setData(result);
				}
			} catch (err: unknown) {
				if (mounted) {
					setError(err as Error);
				}
			} finally {
				if (mounted) {
					setIsLoading(false);
				}
			}
		}

		load();

		return () => {
			mounted = false;
		};
	}, []);

	return { data, isLoading, error };
}
