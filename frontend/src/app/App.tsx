import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';

import ThemeProvider from './providers/ThemeProvider';

import IssueListPage from '@/pages/issues';
import IssueDetailPage from '@/pages/issues/[id]';
import IssueCreatePage from '@/pages/issues/new';
import LabelListPage from '@/pages/labels';
import LoginPage from '@/pages/login';
import MilestoneListPage from '@/pages/milestones';

import { Toaster } from '@/shared/ui/Toaster';
const App = () => {
	return (
		<ThemeProvider>
			<BrowserRouter>
				<Routes>
					<Route path='/' element={<Navigate to='/issues' replace />} />
					<Route path='/login' element={<LoginPage />} />
					<Route path='/issues' element={<IssueListPage />} />
					<Route path='/issues/new' element={<IssueCreatePage />} />
					<Route path='/issues/:id' element={<IssueDetailPage />} />
					<Route path='/labels' element={<LabelListPage />} />
					<Route path='/milestones' element={<MilestoneListPage />} />
					<Route path='*' element={<Navigate to='/issues' replace />} />
				</Routes>
				<Toaster />
			</BrowserRouter>
		</ThemeProvider>
	);
};

export default App;
