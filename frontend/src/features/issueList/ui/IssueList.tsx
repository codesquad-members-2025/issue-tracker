import Container from '@/app/layout/Container';
import type { Issue } from '@/entities/issue/issue';
import { IssueItem } from './IssueItem';

interface IssueListProps {
	issues: Issue[];
}

export function IssueList({ issues }: IssueListProps) {
	return (
		<Container>
			<ul className='space-y-2'>
				{issues.map((issue) => (
					<IssueItem key={issue.id} issue={issue} />
				))}
			</ul>
		</Container>
	);
}
