import { useThemeStore } from '@/shared/theme/useThemeStore';
import { Button } from '@/shared/ui/button';

export function ThemeToggleButton() {
	const { theme, cycleTheme } = useThemeStore();

	return (
		<Button
			variant='theme'
			size='sm'
			onClick={() => cycleTheme()}
			aria-label='Toggle theme'
		>
			Theme {theme}
		</Button>
	);
}
