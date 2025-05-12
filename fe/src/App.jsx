import { useState } from 'react';
import { ThemeProvider } from 'styled-components';
import { darkTheme, lightTheme } from './styles/foundation';
import GlobalStyle from './styles/GlobalStyle';
import AppRouter from './routes/AppRouter';

function App() {
  //추후에 zustand로 테마 상태 관리 예정
  const [IsDark, setIsDark] = useState(false);

  return (
    <ThemeProvider theme={IsDark ? darkTheme : lightTheme}>
      <GlobalStyle />
      {/* base-ui 컴포넌트 테스트 위치 */}
      <AppRouter />
    </ThemeProvider>
  );
}

export default App;
