import Header from '@/widgets/header/Header';
import { Outlet } from 'react-router-dom';

const AppLayout = () => (
	<>
		<Header />
		<main>
			<Outlet />
		</main>
	</>
);

export default AppLayout;
