import { Input } from '@/shared/ui/input';
import { SearchIcon } from 'lucide-react';
import { useSearchParams } from 'react-router-dom';
export function IssueSearch() {
	const [searchParams] = useSearchParams();
	const queryParams = searchParams.get('status') ?? '';

	return (
		<div className='relative w-full max-w-md'>
			{/* 왼쪽에 검색 아이콘 */}
			<SearchIcon className='absolute left-3 top-1/2 -translate-y-1/2 size-4 text-muted-foreground pointer-events-none' />

			{/* shadcn Input */}
			<Input
				type='text'
				text={queryParams}
				placeholder='is:issue is:open'
				className='w-full pl-10'
			/>
		</div>
	);
}
