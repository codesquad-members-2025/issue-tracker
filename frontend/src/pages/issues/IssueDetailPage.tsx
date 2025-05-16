import type { FC } from 'react';
import { useParams } from 'react-router-dom';

const IssueDetailPage: FC = () => {
	const { id } = useParams<{ id: string }>();

	return (
		<div>
			<h1>이슈 상세</h1>
			<p>이슈 ID: {id}</p>
			{/* TODO: 이슈 상세 컴포넌트 구현 */}
		</div>
	);
};

export default IssueDetailPage;
