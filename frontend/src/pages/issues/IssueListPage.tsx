import { IssueFilter } from '@/features/issues/ui/IssueFilter';
import { IssueSearch } from '@/features/issues/ui/IssueSearch';
import type { FC } from 'react';
import { Outlet } from 'react-router-dom';

const IssueListPage: FC = () => {
	return (
		<>
			<Outlet />
			<div className='flex'>
				<IssueFilter />
				<IssueSearch />
			</div>
		</>
	);
};

export default IssueListPage;
