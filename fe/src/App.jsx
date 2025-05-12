import { useState } from 'react';
import { ThemeProvider } from 'styled-components';
import { darkTheme, lightTheme } from './styles/foundation';
import GlobalStyle from './styles/GlobalStyle';
import AppRouter from './routes/AppRouter';
import CheckBox from './base-ui/utils/CheckBox';
import { DropdownMenuTemplate } from './utils/dropDown/DropdownMenuTemplate';
import CheckedOffCircle from './assets/CheckedOffCircle';
import CheckedOnCircle from './assets/CheckedOnCircle';

function App() {
  //추후에 zustand로 테마 상태 관리 예정
  const [IsDark, setIsDark] = useState(false);

  return (
    <ThemeProvider theme={IsDark ? darkTheme : lightTheme}>
      <GlobalStyle />
      {/* base-ui 컴포넌트 테스트 위치 */}
      <CheckBox isActive={true} isDisabled={true} />
      <DropdownMenuTemplate
        triggerLabel="담당자 필터"
        label="담당자 필터"
        groups={[
          {
            items: [
              {
                label: '담당자가 없는 이슈',
                icon: <CheckedOffCircle />,
                onClick: () => {},
              },
              {
                label: 'samsamis9',
                icon: <CheckedOnCircle />,
                onClick: () => {},
              },
            ],
          },
        ]}
      />
      {/* <AppRouter /> */}
    </ThemeProvider>
  );
}

export default App;
