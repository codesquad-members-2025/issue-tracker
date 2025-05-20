import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

const useCheckBoxStore = create(
  immer(() => ({
    checkBoxEntry: {},
    checkIssue: (id) =>
      set((state) => {
        state.checkBoxEntry[id] = true;
      }),
    unCheckIssue: (id) =>
      set((state) => {
        state.checkBoxEntry[id] = false;
      }),
    allCheckIssue: () =>
      set((state) => {
        Object.keys(state.checkBoxEntry).forEach((key) => {
          state.checkBoxEntry[key] = true;
        });
      }),
    allUnCheckIssue: () =>
      set((state) => {
        Object.keys(state.checkBoxEntry).forEach((key) => {
          state.checkBoxEntry[key] = false;
        });
      }),
    deleteIssueToEntry: (id) =>
      set((state) => {
        delete state.checkBoxEntry[id];
      }),
    initEntry: (issues) =>
      set((state) => {
        issues.forEach(({ id }) => {
          state.checkBoxEntry[id] = false;
        });
      }),
  })),
);
