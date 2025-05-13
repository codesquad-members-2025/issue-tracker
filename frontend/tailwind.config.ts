import type { Config } from 'tailwindcss';

/** @type {import('tailwindcss').Config} */
const config: Config = {
	content: ['./src/**/*.{ts,tsx,js,jsx}'],
	theme: {
		extend: {
			colors: {
				// Grayscale
				grayscale50: 'var(--color-grayscale-50)',
				grayscale100: 'var(--color-grayscale-100)',
				grayscale200: 'var(--color-grayscale-200)',
				grayscale300: 'var(--color-grayscale-300)',
				grayscale400: 'var(--color-grayscale-400)',
				grayscale500: 'var(--color-grayscale-500)',
				grayscale600: 'var(--color-grayscale-600)',
				grayscale700: 'var(--color-grayscale-700)',
				grayscale800: 'var(--color-grayscale-800)',
				grayscale900: 'var(--color-grayscale-900)',

				// Accent
				accentBlue: 'var(--color-accent-blue)',
				accentNavy: 'var(--color-accent-navy)',
				accentRed: 'var(--color-accent-red)',
			},
			borderRadius: {
				half: 'var(--radius-half)',
				medium: 'var(--radius-medium)',
				large: 'var(--radius-large)',
			},
			borderWidth: {
				DEFAULT: 'var(--border-width-default)',
				icon: 'var(--border-width-icon)',
				dash: 'var(--border-width-dash)',
			},
			borderStyle: {
				DEFAULT: 'var(--border-style-default)',
				icon: 'var(--border-style-icon)',
				dash: 'var(--border-style-dash)',
			},
			opacity: {
				hover: 'var(--opacity-hover)',
				press: 'var(--opacity-press)',
				disabled: 'var(--opacity-disabled)',
				DEFAULT: 'var(--opacity-default)',
			},
			boxShadow: {
				light: 'var(--shadow-light)',
				dark: 'var(--shadow-dark)',
			},
			fontFamily: {
				sans: ['var(--font-family-base)', 'sans-serif'],
			},
			fontSize: {
				// display-bold
				'display-bold-32': ['32px', { lineHeight: '48px' }],
				'display-bold-24': ['24px', { lineHeight: '36px' }],
				'display-bold-20': ['20px', { lineHeight: '32px' }],
				'display-bold-16': ['16px', { lineHeight: '24px' }],
				'display-bold-12': ['12px', { lineHeight: '16px' }],
				// display-medium
				'display-medium-32': ['32px', { lineHeight: '48px' }],
				'display-medium-24': ['24px', { lineHeight: '36px' }],
				'display-medium-20': ['20px', { lineHeight: '32px' }],
				'display-medium-16': ['16px', { lineHeight: '24px' }],
				'display-medium-12': ['12px', { lineHeight: '16px' }],
			},
			fontWeight: {
				// maps to CSS variable font-display-bold-*, but Tailwind only uses numeric keys
				700: '700',
				500: '500',
			},
		},
	},
	plugins: [],
};

export default config;
