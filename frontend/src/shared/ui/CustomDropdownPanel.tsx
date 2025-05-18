import CheckOffCircleIcon from '@/assets/checkOffCircle.svg?react';
import CheckOnCircleIcon from '@/assets/checkOnCircle.svg?react';
import ChevronDownIcon from '@/assets/chevronDown.svg?react';
import { useEffect, useRef, useState } from 'react';
import { cn } from '../utils/shadcn-utils';

export interface DropdownOption {
	id: number;
	value: string;
	display: string;
	imageUrl?: string;
}

interface CustomDropdownPanelProps {
	label: string;
	panelLabel: string;
	options: DropdownOption[];
	value: string | null;
	onChange: (value: string | null) => void;
	className?: string;
}

export function CustomDropdownPanel({
	label,
	panelLabel,
	options,
	value,
	onChange,
	className = '',
}: CustomDropdownPanelProps) {
	const [open, setOpen] = useState(false);
	const [animating, setAnimating] = useState<'in' | 'out' | null>(null);
	const [alignRight, setAlignRight] = useState(false);
	const selectRef = useRef<HTMLDivElement | null>(null);
	const triggerRef = useRef<HTMLButtonElement | null>(null);

	// value: null → 'none' (미선택 시 구분)
	const currentValue = value ?? 'none';

	// open 시 트리거 버튼의 x 위치로 정렬 방식 결정
	useEffect(() => {
		if (open && triggerRef.current) {
			const rect = triggerRef.current.getBoundingClientRect();
			const windowWidth = window.innerWidth;
			// 화면 오른쪽 30% 이내면 오른쪽 정렬, 아니면 왼쪽 정렬
			setAlignRight(rect.left > windowWidth * 0.7);
		}
	}, [open]);

	// 패널 열기/닫기 애니메이션 제어
	useEffect(() => {
		if (open) {
			setAnimating('in');
		} else if (animating === 'in') {
			setAnimating('out');
			const timer = setTimeout(() => setAnimating(null), 120); // 애니메이션 길이와 동일하게
			return () => clearTimeout(timer);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [open]);

	// 드롭다운 외부 클릭시 닫힘
	useEffect(() => {
		if (!open) return;
		const handleClick = (e: MouseEvent) => {
			if (!selectRef.current?.contains(e.target as Node)) setOpen(false);
		};
		window.addEventListener('mousedown', handleClick);
		return () => window.removeEventListener('mousedown', handleClick);
	}, [open]);

	return (
		<div ref={selectRef} className={`relative ${className}`}>
			{/* Trigger Button */}
			<button
				ref={triggerRef}
				type='button'
				className='w-[80px] h-[32px] flex items-center justify-center gap-1 bg-transparent font-bold text-[16px] leading-6'
				style={{ color: 'var(--neutral-text-default)' }}
				onClick={() => setOpen((o) => !o)}
				aria-haspopup='listbox'
				aria-expanded={open}
			>
				{label}
				<ChevronDownIcon className='size-4 ml-1' />
			</button>

			{/* 애니메이션을 위해 animating이 있을 때만 패널 렌더 */}
			{(open || animating === 'out') && (
				<div
					data-state={open ? 'open' : 'closed'}
					className={cn(
						'absolute mt-2 w-[240px] bg-[var(--neutral-surface-default)] rounded-[16px] shadow-lg border border-[var(--neutral-border-default)] overflow-hidden z-50',
						'transition-all duration-120',
						'bg-popover text-popover-foreground',
						'data-[state=open]:animate-in data-[state=closed]:animate-out',
						'data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0',
						'data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-95',
						alignRight ? 'right-0' : 'left-0',
					)}
					style={{
						boxShadow: 'var(--shadow-light)',
						visibility: open ? 'visible' : 'hidden',
						pointerEvents: open ? 'auto' : 'none',
					}}
					role='listbox'
					tabIndex={-1}
				>
					{/* 헤더 */}
					<div
						className='font-display-bold-16 px-4 py-2'
						style={{
							background: 'var(--neutral-surface-default)',
							color: 'var(--neutral-text-weak)',
							borderTopLeftRadius: 16,
							borderTopRightRadius: 16,
							minHeight: 32,
							height: 32,
							display: 'flex',
							alignItems: 'center',
						}}
					>
						{panelLabel}
					</div>
					{/* 옵션 목록 */}
					<div>
						{options.map((opt, idx) => {
							const isSelected = currentValue === opt.value;
							return (
								<button
									key={opt.id}
									type='button'
									className={`flex items-center gap-2 w-full text-left
                    px-4 py-2 min-h-[44px] h-[44px] cursor-pointer
                    text-[var(--neutral-text-default)] font-display-medium-16
                    bg-[var(--neutral-surface-strong)]
                    border-b border-[var(--neutral-border-default)] last:border-b-0
                    transition hover:bg-[var(--neutral-surface-bold)]
                    ${idx === options.length - 1 ? 'rounded-b-[16px]' : ''}
                  `}
									style={{
										background: 'var(--neutral-surface-strong)',
										...(isSelected && {
											fontWeight: 700,
											color: 'var(--neutral-text-strong)',
										}),
									}}
									onClick={() => {
										setOpen(false);
										onChange(opt.value === 'none' ? null : opt.value);
									}}
									aria-selected={isSelected}
									role='option'
									tabIndex={0}
								>
									{/* 아바타 */}
									{opt.imageUrl ? (
										<img
											src={opt.imageUrl}
											alt={opt.display}
											className='w-6 h-6 rounded-full object-cover'
										/>
									) : (
										<div className='w-6 h-6 rounded-full bg-gray-200 flex items-center justify-center text-xs text-gray-500'>
											{opt.display.charAt(0)}
										</div>
									)}
									{/* 텍스트 */}
									<span>{opt.display}</span>
									{/* 체크박스 아이콘 */}
									<span className='ml-auto relative w-4 h-4 flex items-center justify-center'>
										{isSelected ? (
											<CheckOnCircleIcon className='size-4' />
										) : (
											<CheckOffCircleIcon className='size-4' />
										)}
									</span>
								</button>
							);
						})}
					</div>
				</div>
			)}
		</div>
	);
}
