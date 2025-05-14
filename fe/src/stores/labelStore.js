import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

const useLabelStore = create(
  immer((set) => ({
    labels: [],
    isLoading: false,
    error: null,

    setLabels: (labels) =>
      set((state) => {
        state.labels = labels;
      }),

    fetchLabels: async () => {
      set((state) => {
        state.isLoading = true;
        state.error = null;
      });
      try {
        const res = await fetch('/api/labels');
        if (!res.ok) throw new Error('Failed to fetch labels');
        const data = await res.json();
        set((state) => {
          state.labels = data;
          state.isLoading = false;
        });
      } catch (err) {
        set((state) => {
          state.error = err.message;
          state.isLoading = false;
        });
      }
    },

    addLabel: (label) =>
      set((state) => {
        state.labels.push(label);
      }),

    removeLabel: (id) =>
      set((state) => {
        state.labels = state.labels.filter((label) => label.id !== id);
      }),

    updateLabel: (updatedLabel) =>
      set((state) => {
        const index = state.labels.findIndex((l) => l.id === updatedLabel.id);
        if (index !== -1) {
          state.labels[index] = updatedLabel;
        }
      }),
  })),
);

export default useLabelStore;
