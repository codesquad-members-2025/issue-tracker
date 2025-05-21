import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

export const useAuthStore = create(
  immer((set) => ({
    loginId: null,
    imgUrl: null,
    accessToken: null,

    resetUser: () =>
      set((state) => {
        state.loginId = null;
        state.imgUrl = null;
        state.accessToken = null;
      }),
    setUser: (loginId, imgUrl, accessToken) =>
      set((state) => {
        state.loginId = loginId;
        state.imgUrl = imgUrl;
        state.accessToken = accessToken;
      }),
  })),
);
