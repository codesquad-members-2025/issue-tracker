import CheckOffCircleIcon from '@/assets/checkOffCircle.svg?react';
import CheckOnCircleIcon from '@/assets/checkOnCircle.svg?react';
import ChevronDownIcon from '@/assets/chevronDown.svg?react';
import { useEffect, useRef, useState } from 'react';

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
	const selectRef = useRef<HTMLDivElement | null>(null);

	// 드롭다운 외부 클릭시 닫힘
	useEffect(() => {
		if (!open) return;
		const handleClick = (e: MouseEvent) => {
			if (!selectRef.current?.contains(e.target as Node)) setOpen(false);
		};
		window.addEventListener('mousedown', handleClick);
		return () => window.removeEventListener('mousedown', handleClick);
	}, [open]);

	// value: null → 'none' (미선택 시 구분)
	const currentValue = value ?? 'none';

	return (
		<div ref={selectRef} className={`relative ${className}`}>
			{/* Trigger Button */}
			<button
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

			{open && (
				<div
					className='absolute left-0 mt-2 w-[240px] bg-[var(--neutral-surface-default)] rounded-[16px] shadow-lg border border-[var(--neutral-border-default)] overflow-hidden z-50'
					style={{ boxShadow: 'var(--shadow-light)' }}
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
									className={`
                    flex items-center gap-2 w-full text-left
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
