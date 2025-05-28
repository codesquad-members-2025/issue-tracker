import type { ReactNode } from 'react';
import { ThemeProvider as EmotionThemeProvider, Global } from '@emotion/react';
import { lightTheme, darkTheme } from './theme';
import globalStyle from './globalStyles';
import { useThemeContext } from '@/shared/context/ThemeContext';

interface Props {
  children: ReactNode;
}

export function ThemeProvider({ children }: Props) {
  const { isDarkMode } = useThemeContext();
  const theme = isDarkMode ? darkTheme : lightTheme;

  return (
    <EmotionThemeProvider theme={theme}>
      <Global styles={globalStyle(theme)} />
      {children}
    </EmotionThemeProvider>
  );
}
