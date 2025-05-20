import { useThemeContext } from '@/shared/context/ThemeContext';

export default function DarkModeToggle() {
  const { isDarkMode, toggleTheme } = useThemeContext();

  return <button onClick={toggleTheme}>{isDarkMode ? '☀️' : '🌙'}</button>;
}
