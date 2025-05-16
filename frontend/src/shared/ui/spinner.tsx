import { cn } from '@/shared/utils/shadcn-utils';

export interface SpinnerProps {
	/** 스피너의 크기(px) */
	size?: number;
	/** 추가적인 클래스명 */
	className?: string;
}

export function Spinner({ size = 24, className }: SpinnerProps) {
	return (
		<div aria-busy='true' className='inline-block'>
			<svg
				className={cn('animate-spin', className)}
				width={size}
				height={size}
				viewBox='0 0 24 24'
			>
				<title>로딩 중</title>
				<circle
					className='opacity-25'
					cx='12'
					cy='12'
					r='10'
					stroke='currentColor'
					strokeWidth='4'
					fill='none'
				/>
				<path
					className='opacity-75'
					fill='currentColor'
					d='M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z'
				/>
			</svg>
		</div>
	);
}
