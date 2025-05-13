export type ThemeVariables = Record<string, string>;

export const lightTheme: ThemeVariables = {
	'--color-text-weak': 'var(--color-grayscale-600)',
	'--color-text-default': 'var(--color-grayscale-700)',
	'--color-text-strong': 'var(--color-grayscale-900)',
	'--color-surface-default': 'var(--color-grayscale-100)',
	'--color-surface-bold': 'var(--color-grayscale-200)',
	'--color-border-default': 'var(--color-grayscale-300)',
	'--color-border-active': 'var(--color-grayscale-900)',

	'--color-brand-text-weak': 'var(--color-accent-blue)',
	'--color-brand-surface': 'var(--color-grayscale-50)',
	'--color-brand-border': 'var(--color-accent-blue)',

	'--color-danger-text': 'var(--color-accent-red)',
	'--color-danger-surface': 'var(--color-accent-red)',
	'--color-danger-border': 'var(--color-accent-red)',
};

export const darkTheme: ThemeVariables = {
	'--color-text-weak': 'var(--color-grayscale-500)',
	'--color-text-default': 'var(--color-grayscale-400)',
	'--color-text-strong': 'var(--color-grayscale-50)',
	'--color-surface-default': 'var(--color-grayscale-900)',
	'--color-surface-bold': 'var(--color-grayscale-700)',
	'--color-border-default': 'var(--color-grayscale-600)',
	'--color-border-active': 'var(--color-grayscale-300)',

	'--color-brand-text-weak': 'var(--color-accent-blue)',
	'--color-brand-surface': 'var(--color-grayscale-900)',
	'--color-brand-border': 'var(--color-accent-blue)',

	'--color-danger-text': 'var(--color-accent-red)',
	'--color-danger-surface': 'var(--color-accent-red)',
	'--color-danger-border': 'var(--color-accent-red)',
};
