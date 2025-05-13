import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

export const useUserStore = create(
  immer((set) => ({
    userId: null,
    imgUrl: null,
    accessToken: '임의 토큰',

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
