import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

const useIssuesStore = create(
  immer((set) => ({
    issues: [],

    setIssues: (issues) =>
      set((state) => {
        state.issues = issues;
      }),

    addIssue: (issue) =>
      set((state) => {
        state.issues.push(issue);
      }),
    toggleIssues: (IssueIdArr) =>
      set((state) => {
        state.issues = state.issues.map((issue) => {
          if (!IssueIdArr.includes(issue.id)) return issue;
          return { ...issue, isOpen: !issue.isOpen };
        });
      }),
    updateIssue: (updatedIssue) =>
      set((state) => {
        const index = state.issues.findIndex((i) => i.id === updatedIssue.id);
        if (index !== -1) {
          state.issues[index] = updatedIssue;
        }
      }),

    removeIssue: (id) =>
      set((state) => {
        state.issues = state.issues.filter((i) => i.id !== id);
      }),
  })),
);

export default useIssuesStore;
