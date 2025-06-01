import type { ReactNode } from 'react';
import { ThemeProvider as EmotionThemeProvider, Global } from '@emotion/react';
import { useThemeContext } from '@/shared/context/ThemeContext';
import { lightTheme, darkTheme } from '@/shared/styles/theme';
import globalStyle from '@/shared/styles/globalStyles';

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
