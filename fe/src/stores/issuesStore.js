import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

const useIssuesStore = create(
  immer((set) => ({
    issues: [],
    issueSummary: {},
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

    parseIssue: () =>
      set((state) => {
        const summary = state.issues.reduce(
          (acc, { id, isOpen }) => {
            if (isOpen) {
              acc.openedIssueId.push(id);
              acc.openIssueNumber += 1;
            } else {
              acc.closedIssueId.push(id);
              acc.closeIssueNumber += 1;
            }
            return acc;
          },
          { openedIssueId: [], closedIssueId: [], openIssueNumber: 0, closeIssueNumber: 0 },
        );
        state.issueSummary = summary;
      }),
  })),
);

export default useIssuesStore;
