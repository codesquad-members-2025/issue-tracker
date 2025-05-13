import { create } from "zustand";
import { persist } from "zustand/middleware";

type Theme = "light" | "dark";

interface ThemeState {
  /** 현재 테마 값 */
  theme: Theme;
  /** 테마 토글 함수 */
  toggleTheme: () => void;
  /** 강제로 테마 설정 */
  setTheme: (t: Theme) => void;
}

/**
 * zustand 미들웨어
 * 1) persist : localStorage에 "theme" 키로 저장해 새로고침 후에도 유지
 *    └─ Next 15 App Router에서도 안전하게 동작 (CSR 구간에서만 접근)
 */
export const useStore = create<ThemeState>()(
  persist(
    (set) => ({
      theme: "light",
      toggleTheme: () =>
        set((s) => ({ theme: s.theme === "light" ? "dark" : "light" })),
      setTheme: (t) => set({ theme: t }),
    }),
    { name: "theme" } // localStorage key
  )
);
