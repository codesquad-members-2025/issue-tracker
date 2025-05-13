import type { ThemeVariables } from './themes';
import { darkTheme, /*, 다양한 테마들 */ lightTheme } from './themes';

// ① themesMap에 새 테마를 추가하세요
const themesMap = {
	light: lightTheme,
	dark: darkTheme,
	/* 다양한 테마들 */
} as const;

// ② 키 목록을 타입 안전하게 추출
const themeNames = Object.keys(themesMap) as Array<keyof typeof themesMap>;

/**
 * 초기 테마 결정:
 * - 로컬스토리지에 저장된 값이 있으면 사용
 * - 없으면 OS 다크모드 선호도를 보고 light/dark 중 하나 선택
 * - 다크/라이트 외 커스텀 테마만 있다면 themeNames[0]을 반환
 */
// 나중에 be에서 user 정보에 테마도 저장하도록 하자
function getInitialTheme(): keyof typeof themesMap {
	// localStorage.getItem()의 반환 타입은 string | null입니다.
	const stored = localStorage.getItem('theme');
	if (stored && (themeNames as readonly string[]).includes(stored)) {
		return stored as keyof typeof themesMap;
	}

	// OS 다크모드가 있고 dark 테마가 아닌 경우만 고려
	if (
		window.matchMedia('(prefers-color-scheme: dark)').matches &&
		themesMap.dark
	) {
		return 'dark';
	}

	// 그 외 기본값
	return themeNames[0];
}

/**
 * CSS 변수 주입
 * document.documentElement에 모두 setProperty
 */
function applyCssVariables(vars: ThemeVariables) {
	for (const [key, value] of Object.entries(vars)) {
		document.documentElement.style.setProperty(key, value);
	}
}

export { themesMap, themeNames, getInitialTheme, applyCssVariables };
