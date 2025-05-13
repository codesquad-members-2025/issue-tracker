import { useThemeStore } from '@/shared/theme/store';
import { applyCssVariables, getInitialTheme } from '@/shared/theme/utils';
import { type PropsWithChildren, useEffect } from 'react';

export default function ThemeProvider({ children }: PropsWithChildren) {
	const { themeName, variables, setTheme } = useThemeStore();

	// 1) 초기 테마 결정
	useEffect(() => {
		const initial = getInitialTheme();
		setTheme(initial);
	}, [setTheme]);

	// 2) CSS 변수 적용 & 로컬스토리지 업데이트
	useEffect(() => {
		applyCssVariables(variables);
		localStorage.setItem('theme', themeName);
	}, [variables, themeName]);

	return <>{children}</>;
}
