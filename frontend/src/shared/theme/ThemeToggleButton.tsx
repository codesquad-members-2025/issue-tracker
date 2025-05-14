import { Button } from '@/shared/ui/button';
import { useThemeStore } from './store';

export function ThemeToggleButton() {
	const { themeName, cycleTheme } = useThemeStore();

	return (
		<Button
			variant='theme'
			size='sm'
			onClick={cycleTheme}
			aria-label='Toggle theme'
		>
			Theme {themeName}
		</Button>
	);
}
