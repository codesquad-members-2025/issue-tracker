"use client";

import { ThemeProvider } from "@emotion/react";
import { lightTheme, darkTheme } from "@/styles/theme";
import { useStore } from "@/lib/theme-store"; // Zustand 예시

export default function StyledApp({ children }: { children: React.ReactNode }) {
  const theme = useStore((s) => s.theme); // 'light' | 'dark'
  return (
    <ThemeProvider theme={theme === "light" ? lightTheme : darkTheme}>
      {children}
    </ThemeProvider>
  );
}
