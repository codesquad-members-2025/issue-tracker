import { IssueList, useIssueList } from '@/features/issueList';
import {
	IssueCreationButton,
	IssueFilter,
	IssueSearch,
	LabelListButton,
	MilestoneListButton,
} from '@/features/issueList/widget';
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
			<div className='flex items-center mt-8 mb-6 justify-between flex-wrap'>
				<div className='flex'>
					<IssueFilter />
					<IssueSearch />
				</div>
				<div className='flex gap-4'>
					<div className='flex border border-[var(--neutral-border-default)] rounded-2xl'>
						<LabelListButton />
						<div className='border-r border-[var(--neutral-border-default)]' />
						<MilestoneListButton />
					</div>
					<IssueCreationButton />
				</div>
			</div>
			{data && <IssueList issues={data.issues} />}
		</>
	);
};

export default IssueListPage;
