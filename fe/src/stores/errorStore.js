import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

export const useErrorStore = create(
  immer((set) => ({
    type: null,
    errorMessage: null,
    setError: (type, message) =>
      set((state) => {
        state.type = type;
        state.errorMessage = message;
      }),
    clearError: () =>
      set((state) => {
        state.type = null;
        state.errorMessage = null;
      }),
  })),
);
