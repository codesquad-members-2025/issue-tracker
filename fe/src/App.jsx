import { ThemeProvider } from 'styled-components';
import { darkTheme, lightTheme } from './styles/foundation';
import GlobalStyle from './styles/GlobalStyle';
import AppRouter from './routes/AppRouter';
import { useUiStore } from './stores/uiStore';
import ErrorToast from './utils/errorToast';

function App() {
  const isDark = useUiStore((state) => state.isDarkMode);
  return (
    <ThemeProvider theme={isDark ? darkTheme : lightTheme}>
      <GlobalStyle />
      {/* base-ui 컴포넌트 테스트 위치 */}
      <AppRouter />
      <ErrorToast />
    </ThemeProvider>
  );
}

export default App;
