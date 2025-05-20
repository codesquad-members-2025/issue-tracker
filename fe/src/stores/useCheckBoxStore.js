import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

const useCheckBoxStore = create(
  immer((set) => ({
    checkBoxEntry: {},

    toggleCheckBox: (id) =>
      set((state) => {
        state.checkBoxEntry[id] = !state.checkBoxEntry[id];
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

    //서버에서 다시 새로운 데이터를 받아오면 이 로직은 필요가 없다.
    deleteIssueToEntry: (id) =>
      set((state) => {
        delete state.checkBoxEntry[id];
      }),
    setEntry: (issues) =>
      set((state) => {
        issues.forEach(({ id }) => {
          state.checkBoxEntry[id] = false;
        });
      }),
  })),
);

export default useCheckBoxStore;
