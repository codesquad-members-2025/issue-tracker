import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

export const useUiStore = create(
  immer((set) => ({
    isDarkMode: false,
    toggleDarkMode: () =>
      set((state) => {
        state.isDarkMode = !state.isDarkMode;
      }),
  })),
);
