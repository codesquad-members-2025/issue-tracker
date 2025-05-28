import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import LoginPage from '@/pages/LoginPage';
import IssueListPage from '@/pages/IssueListPage';
import IssueDetailPage from '@/pages/IssueDetailPage';
import LabelsPage from '@/pages/LabelsPage';
import MilestonesPage from '@/pages/MilestonesPage';
import NewIssuePage from '@/pages/NewIssuePage';
import NotFoundPage from '@/pages/NofoundPage';
import MainLayout from '@/layouts/MainLayout';

const router = createBrowserRouter([
  {
    path: '/login',
    element: <LoginPage />,
  },
  {
    path: '/',
    element: <MainLayout />,
    children: [
      { index: true, element: <IssueListPage /> },
      { path: 'issues/:id', element: <IssueDetailPage /> },
      { path: 'issues/new', element: <NewIssuePage /> },
      { path: 'labels', element: <LabelsPage /> },
      { path: 'milestones', element: <MilestonesPage /> },
    ],
  },
  {
    path: '*',
    element: <NotFoundPage />,
  },
]);

export default function AppRouter() {
  return <RouterProvider router={router} />;
}
