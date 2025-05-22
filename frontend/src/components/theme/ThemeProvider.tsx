/** @jsxImportSource @emotion/react */
import type { ReactNode } from "react";
import { ThemeProvider as EmotionThemeProvider } from "@emotion/react";
import { useThemeStore } from "@/lib/useThemeStore";

interface Props {
  children: ReactNode;
}

const ThemeProvider: React.FC<Props> = ({ children }) => {
  const { current } = useThemeStore();
  return (
    <EmotionThemeProvider theme={current}>{children}</EmotionThemeProvider>
  );
};

export default ThemeProvider;
