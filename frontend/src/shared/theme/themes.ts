export type ThemeVariables = Record<string, string>;

export const lightTheme: ThemeVariables = {
	// Neutral Text
	'--neutral-text-weak': 'var(--color-grayscale-600)',
	'--neutral-text-default': 'var(--color-grayscale-700)',
	'--neutral-text-strong': 'var(--color-grayscale-900)',

	// Neutral Surface
	'--neutral-surface-default': 'var(--color-grayscale-100)',
	'--neutral-surface-bold': 'var(--color-grayscale-200)',
	'--neutral-surface-strong': 'var(--color-grayscale-50)',

	// Neutral Border
	'--neutral-border-default': 'var(--color-grayscale-300)',
	'--neutral-border-active': 'var(--color-grayscale-900)',

	// Brand (Accent Blue)
	'--brand-text-weak': 'var(--color-accent-blue)',
	'--brand-text-default': 'var(--color-grayscale-50)',
	'--brand-surface-weak': 'var(--color-grayscale-50)',
	'--brand-surface-default': 'var(--color-accent-blue)',
	'--brand-border-default': 'var(--color-accent-blue)',

	// Danger (Accent Red)
	'--danger-text-default': 'var(--color-accent-red)',
	'--danger-surface-default': 'var(--color-accent-red)',
	'--danger-border-default': 'var(--color-accent-red)',

	// Palette Shortcuts
	'--palette-blue': 'var(--color-accent-blue)',
	'--palette-navy': 'var(--color-accent-navy)',
	'--palette-red': 'var(--color-accent-red)',
};

export const darkTheme: ThemeVariables = {
	// Neutral Text
	'--neutral-text-weak': 'var(--color-grayscale-500)',
	'--neutral-text-default': 'var(--color-grayscale-400)',
	'--neutral-text-strong': 'var(--color-grayscale-50)',

	// Neutral Surface
	'--neutral-surface-default': 'var(--color-grayscale-900)',
	'--neutral-surface-bold': 'var(--color-grayscale-700)',
	'--neutral-surface-strong': 'var(--color-grayscale-800)',

	// Neutral Border
	'--neutral-border-default': 'var(--color-grayscale-600)',
	'--neutral-border-active': 'var(--color-grayscale-300)',

	// Brand (Accent Blue)
	'--brand-text-weak': 'var(--color-accent-blue)',
	'--brand-text-default': 'var(--color-grayscale-50)',
	'--brand-surface-weak': 'var(--color-grayscale-900)',
	'--brand-surface-default': 'var(--color-accent-blue)',
	'--brand-border-default': 'var(--color-accent-blue)',

	// Danger (Accent Red)
	'--danger-text-default': 'var(--color-accent-red)',
	'--danger-surface-default': 'var(--color-accent-red)',
	'--danger-border-default': 'var(--color-accent-red)',

	// Palette Shortcuts
	'--palette-blue': 'var(--color-accent-blue)',
	'--palette-navy': 'var(--color-accent-navy)',
	'--palette-red': 'var(--color-accent-red)',
};
