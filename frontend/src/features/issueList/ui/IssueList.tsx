import type { Issue } from '@/entities/issue/issue';
import { IssueListHeader } from '../widget';
import { IssueItem } from './IssueItem';

interface IssueListProps {
	issues: Issue[];
}

export function IssueList({ issues }: IssueListProps) {
	return (
		<div className='flex flex-col rounded-2xl border border-[var(--neutral-border-default)] divide-y divide-[var(--neutral-border-default)] text-[var(--neutral-border-default)] '>
			<IssueListHeader />
			{issues.map((issue) => (
				<IssueItem key={issue.id} issue={issue} />
			))}
		</div>
	);
}
