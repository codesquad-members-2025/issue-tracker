import { IssueList, useIssueList } from '@/features/issueList';
import { IssueFilter, IssueSearch } from '@/features/issueList/widget';
import { Spinner } from '@/shared/ui/spinner';
import type { FC } from 'react';
import { Outlet } from 'react-router-dom';

const IssueListPage: FC = () => {
	const { data, isLoading, error } = useIssueList();

	if (isLoading) {
		return (
			<div className='flex justify-center items-center h-full'>
				<Spinner />
			</div>
		);
	}

	if (error) {
		return (
			<div className='text-center text-red-600'>
				이슈를 불러오는 중 오류가 발생했습니다.
			</div>
		);
	}

	return (
		<>
			<Outlet />
			<div className='flex items-center mt-8 mb-6'>
				<IssueFilter />
				<IssueSearch />
			</div>
			<div className='flex flex-col rounded-2xl border border-[var(--neutral-border-default)] text-[var(--neutral-border-default)]'>
				{data && <IssueList issues={data.issues} />}
			</div>
		</>
	);
};

export default IssueListPage;
