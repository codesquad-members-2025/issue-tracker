/*
로그인 ID (nickname)
비밀번호
devchan - 1234
alice - 1234
bob - 1234
charlie - 1234
david - 1234
eve - 1234

*/

import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';
import { persist } from 'zustand/middleware';

export const useAuthStore = create(
  persist(
    immer((set) => ({
      loginId: null,
      profileImageUrl: null,
      accessToken: null,

      resetUser: () =>
        set((state) => {
          state.loginId = null;
          state.profileImageUrl = null;
          state.accessToken = null;
        }),
      setUser: (loginId, profileImageUrl, accessToken) =>
        set((state) => {
          state.loginId = loginId;
          state.profileImageUrl = profileImageUrl;
          state.accessToken = accessToken;
        }),
    })),

    {
      name: 'auth-store',
      getStorage: () => localStorage,
      partialize: (state) => ({
        loginId: state.loginId,
        profileImageUrl: state.profileImageUrl,
        accessToken: state.accessToken,
      }),
    },
  ),
);
