// shared/theme/store.ts
import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';
import type { ThemeVariables } from './themes';
import { themeNames, themesMap } from './utils';

export type ThemeName = (typeof themeNames)[number];

interface ThemeState {
	themeName: ThemeName;
	variables: ThemeVariables;
	setTheme: (name: ThemeName) => void;
	cycleTheme: () => void;
}

export const useThemeStore = create(
	immer<ThemeState>((set, get) => ({
		themeName: themeNames[0], // 기본값: themeNames 배열의 첫 테마
		variables: themesMap[themeNames[0]],

		setTheme: (name) =>
			set((state) => {
				state.themeName = name;
				state.variables = themesMap[name];
			}),

		cycleTheme: () => {
			const names = themeNames;
			const current = get().themeName;
			const idx = names.indexOf(current);
			const next = names[(idx + 1) % names.length];
			set((state) => {
				state.themeName = next;
				state.variables = themesMap[next];
			});
		},
	})),
);
