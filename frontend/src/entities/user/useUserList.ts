import { useEffect, useState } from 'react';
// src/entities/user/hooks/useUserList.ts
import type { UserListData } from './userApi';
import { fetchUsers } from './userApi';

export function useUserList() {
	const [data, setData] = useState<UserListData | null>(null);
	const [isLoading, setIsLoading] = useState<boolean>(true);
	const [error, setError] = useState<Error | null>(null);

	useEffect(() => {
		let mounted = true;

		async function load() {
			setIsLoading(true);
			try {
				const result = await fetchUsers();
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
