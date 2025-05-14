import AppRouter from './providers/Router';
import ThemeProvider from './providers/ThemeProvider';

const App = () => {
	return (
		<ThemeProvider>
			<AppRouter />
		</ThemeProvider>
	);
};

export default App;
