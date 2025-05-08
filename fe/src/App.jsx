import { useState } from 'react';
import { ThemeProvider } from 'styled-components';
import { darkTheme, lightTheme } from './styles/foundation';
import GlobalStyle from './styles/GlobalStyle';
import AppRouter from './routes/AppRouter';
import CheckBox from './base-ui/utils/CheckBox';

function App() {
  //추후에 zustand로 테마 상태 관리 예정
  const [IsDark, setIsDark] = useState(false);

  return (
    <ThemeProvider theme={IsDark ? darkTheme : lightTheme}>
      <GlobalStyle />
      <AppRouter />
    </ThemeProvider>
  );
}

export default App;
