import { ThemeProvider as EmotionThemeProvider } from '@/shared/styles/ThemeProvider';
import { ThemeProvider as ThemeContextProvider } from '@/shared/context/ThemeContext';
import AppRouter from '@/app/router';

export default function App() {
  return (
    <ThemeContextProvider>
      <EmotionThemeProvider>
        <AppRouter />
      </EmotionThemeProvider>
    </ThemeContextProvider>
  );
}
