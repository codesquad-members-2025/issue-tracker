import Header from '@/widgets/header/Header';
import { Outlet } from 'react-router-dom';
import Container from './Container';

const AppLayout = () => (
	<>
		<Container>
			<Header />
		</Container>

		<main>
			<Container>
				<Outlet />
			</Container>
		</main>
	</>
);

export default AppLayout;
