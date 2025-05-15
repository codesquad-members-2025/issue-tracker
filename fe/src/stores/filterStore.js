// fe/src/stores/filterStore.js
import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

const useFilterStore = create(
  immer((set) => ({
    selectedFilters: {},

    setFilter: (key, value) =>
      set((state) => {
        state.selectedFilters[key] = value;
      }),

    resetFilters: () =>
      set((state) => {
        state.selectedFilters = {};
      }),
  })),
);

export default useFilterStore;
