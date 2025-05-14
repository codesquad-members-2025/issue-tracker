import {
	Navigate,
	RouterProvider,
	createBrowserRouter,
} from 'react-router-dom';

import { Toaster } from '@/shared/ui/sonner';

import AppLayout from '@/app/layout/AppLayout';
import NoHeaderLayout from '@/app/layout/NoHeaderLayout';

import LabelListPage from '@/pages/LabelListPage';
import LoginPage from '@/pages/LoginPage';
import MilestoneListPage from '@/pages/MilestoneListPage';
import IssueDetailPage from '@/pages/issues/IssueDetailPage';
import IssueListPage from '@/pages/issues/IssueListPage';

import IssueCreateModal from '@/features/issues/create/ui/IssueCreateModal';

const router = createBrowserRouter([
	{
		element: <NoHeaderLayout />,
		children: [{ path: '/login', element: <LoginPage /> }],
	},
	{
		element: <AppLayout />,
		children: [
			{ path: '/', element: <Navigate to='/issues' replace /> },
			{
				path: '/issues',
				element: <IssueListPage />,
				children: [{ path: 'new', element: <IssueCreateModal /> }],
			},
			{ path: '/issues/:id', element: <IssueDetailPage /> },
			{ path: '/labels', element: <LabelListPage /> },
			{ path: '/milestones', element: <MilestoneListPage /> },
			{ path: '*', element: <Navigate to='/issues' replace /> },
		],
	},
]);

const AppRouter = () => {
	return (
		<>
			<RouterProvider router={router} />
			<Toaster />
		</>
	);
};

export default AppRouter;
