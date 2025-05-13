import type { FC } from 'react';
import { Outlet } from 'react-router-dom';

const IssueListPage: FC = () => {
	return (
		<>
			<div>
				<h1>이슈 목록</h1>
				{/* TODO: 이슈 목록 컴포넌트 구현 */}
			</div>
			<Outlet />
		</>
	);
};

export default IssueListPage;
