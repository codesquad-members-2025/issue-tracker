import {
	applyCssVariables,
	flashThemeTransition,
	getInitialTheme,
	themeNames,
	themesMap,
} from '@/shared/theme';
import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

type ThemeName = keyof typeof themesMap;

interface ThemeState {
	theme: ThemeName;
	setTheme: (name: ThemeName) => void;
	cycleTheme: () => void;
}

export const useThemeStore = create<ThemeState>()(
	immer((set, get) => {
		const initial = getInitialTheme();
		applyCssVariables(themesMap[initial]);

		return {
			theme: initial,

			setTheme: (name) => {
				if (!themeNames.includes(name)) {
					console.warn(`'${name}'은 유효한 테마가 아닙니다.`);
					return;
				}
				localStorage.setItem('theme', name);
				applyCssVariables(themesMap[name]);
				flashThemeTransition(300);
				set((state) => {
					state.theme = name;
				});
			},

			cycleTheme: () => {
				const { theme } = get();
				const currentIndex = themeNames.indexOf(theme);
				const nextIndex = (currentIndex + 1) % themeNames.length;
				const nextTheme = themeNames[nextIndex];

				localStorage.setItem('theme', nextTheme);
				applyCssVariables(themesMap[nextTheme]);
				flashThemeTransition(300);
				set((state) => {
					state.theme = nextTheme;
				});
			},
		};
	}),
);
