import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

export const useAuthStore = create(
  immer((set) => ({
    userId: 106,
    imgUrl: null,
    accessToken: null,

    resetUser: () =>
      set((state) => {
        state.userId = null;
        state.imgUrl = null;
        state.accessToken = null;
      }),
    setUser: ({ userId, imgUrl, accessToken }) =>
      set((state) => {
        state.userId = userId;
        state.imgUrl = imgUrl;
        state.accessToken = accessToken;
      }),
  })),
);
