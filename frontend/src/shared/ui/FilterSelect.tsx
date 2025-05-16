import {
	Select,
	SelectContent,
	SelectGroup,
	SelectItem,
	SelectLabel,
	SelectScrollDownButton,
	SelectScrollUpButton,
	SelectSeparator,
	SelectTrigger,
} from '@/shared/ui/select';
import { cn } from '@/shared/utils/shadcn-utils';

export interface FilterOption {
	value: string;
	label: string;
}

export interface FilterSelectProps {
	label: string;
	options: FilterOption[];
	value: string;
	onChange: (value: string) => void;
	placeholder?: string;
	size?: 'default' | 'sm';
	className?: string;
}

export function FilterSelect({
	label,
	options,
	value,
	onChange,
	placeholder = '필터',
	size = 'default',
	className,
}: FilterSelectProps) {
	return (
		<div className={cn('flex flex-col space-y-1', className)}>
			<Select value={value} onValueChange={onChange}>
				{/* Trigger: 항상 placeholder 로고만 보여줌 */}
				<SelectTrigger size={size} className='w-32 justify-between'>
					<span className='text-sm'>{placeholder}</span>
				</SelectTrigger>

				<SelectContent>
					<SelectScrollUpButton />

					<SelectGroup>
						<SelectLabel>{label}</SelectLabel>
						<SelectSeparator />

						{options.map((opt) => (
							<SelectItem key={opt.value} value={opt.value}>
								{opt.label}
							</SelectItem>
						))}
					</SelectGroup>

					<SelectScrollDownButton />
				</SelectContent>
			</Select>
		</div>
	);
}
