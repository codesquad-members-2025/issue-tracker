import { Slot } from '@radix-ui/react-slot';
import { type VariantProps, cva } from 'class-variance-authority';
import * as React from 'react';

import { cn } from '@/shared/utils/shadcn-utils';

const buttonVariants = cva(
	'inline-flex items-center justify-center gap-2 whitespace-nowrap font-medium outline-none transition-all',
	{
		variants: {
			variant: {
				contained: [
					// 배경 · 텍스트 · 그림자 · 상태별 불투명도
					'bg-[var(--brand-surface-default)]',
					'text-[var(--brand-text-default)]',
					'shadow-[var(--shadow-light)]',
					'hover:opacity-[var(--opacity-hover)]',
					'active:opacity-[var(--opacity-press)]',
					'disabled:opacity-[var(--opacity-disabled)]',
					'disabled:pointer-events-none',
				],
				outline: [
					'bg-transparent',
					'[border:var(--border-default)_var(--brand-border-default)]',
					'text-[var(--color-accent-blue)]',
					'hover:bg-[var(--color-accent-blue)]',
					'hover:text-[var(--color-grayscale-50)]',
					'hover:opacity-[var(--opacity-hover)]',
					'active:opacity-[var(--opacity-press)]',
					'disabled:opacity-[var(--opacity-disabled)]',
					'disabled:pointer-events-none',
				],
				ghost: [
					'bg-transparent',
					'text-[var(--neutral-text-default)]',
					'hover:bg-[var(--color-accent-blue)/10]',
					'hover:opacity-[var(--opacity-hover)]',
					'active:bg-[var(--color-accent-blue)/20]',
					'disabled:opacity-[var(--opacity-disabled)]',
					'disabled:pointer-events-none',
				],
			},
			size: {
				lg: [
					'w-[240px] h-[56px] px-[24px]',
					'font-[var(--font-display-medium-20)]',
					'rounded-[var(--radius-large)]',
				],
				md: [
					'w-[184px] h-[48px] px-[24px]',
					'font-[var(--font-display-medium-16)]',
					'rounded-[var(--radius-medium)]',
				],
				sm: [
					'w-[128px] h-[40px] px-[16px]',
					'font-[var(--font-display-medium-12)]',
					'rounded-[var(--radius-medium)]',
				],
			},
			pattern: {
				text: [], // 텍스트 전용
				'icon-text': [], // gap-2 로 아이콘+텍스트 같이 사용
			},
			animation: {
				none: [],
				grow: ['transform', 'transition-transform', 'hover:scale-105'],
				pop: [
					'transform',
					'transition-transform',
					'hover:-translate-y-1',
					'hover:scale-105',
				],
			},
		},
		defaultVariants: {
			variant: 'contained',
			size: 'md',
			pattern: 'text',
			animation: 'none',
		},
	},
);

export interface ButtonProps
	extends React.ButtonHTMLAttributes<HTMLButtonElement>,
		VariantProps<typeof buttonVariants> {
	/**
	 * asChild = true 이면 Radix Slot을 통해 Link 등 커스텀 컴포넌트에 스타일 전파
	 */
	asChild?: boolean;
}

export const Button = React.forwardRef<HTMLButtonElement, ButtonProps>(
	(
		{
			variant,
			size,
			pattern,
			animation,
			asChild = false,
			className,
			children,
			...props
		},
		ref,
	) => {
		const Comp = asChild ? Slot : 'button';
		return (
			<Comp
				ref={ref}
				className={cn(
					buttonVariants({ variant, size, pattern, animation, className }),
				)}
				{...props}
			>
				{children}
			</Comp>
		);
	},
);
Button.displayName = 'Button';
