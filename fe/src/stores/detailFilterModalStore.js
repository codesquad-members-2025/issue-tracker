// useFilterStore.js
import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

const useFilterModalStore = create(
  immer((set) => ({
    isActive: false, // 모달의 열림/닫힘 상태 관리

    filterEntry: {
      users: null, // 담당자 목록 & 작성자 목록
      labels: null,
      milestones: null,
    },

    setFilterData: (users, labels, milestones) =>
      set((state) => {
        state.users = users;
        state.labels = labels;
        state.milestones = milestones;
      }),

    openModal: () =>
      set((state) => {
        state.isActive = true;
      }),
    closeModal: () =>
      set((state) => {
        state.isActive = false;
      }),
  })),
);

export default useFilterModalStore;
