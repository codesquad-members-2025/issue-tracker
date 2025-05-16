import { type FilterOption, FilterSelect } from '@/shared/ui/FilterSelect';
import { useSearchParams } from 'react-router-dom';

// “열린 이슈” 등 옵션 목록
const statusOptions: FilterOption[] = [
	{ value: 'open', label: '열린 이슈' },
	{ value: 'mine', label: '내가 작성한 이슈' },
	{ value: 'assigned', label: '나에게 할당된 이슈' },
	{ value: 'commented', label: '내가 댓글 남긴 이슈' },
	{ value: 'closed', label: '닫힌 이슈' },
];

export function IssueFilter() {
	const [searchParams, setSearchParams] = useSearchParams();

	// 쿼리에서 status 읽어오기 (없으면 빈 문자열)
	const current = searchParams.get('status') ?? '';

	// 값 변경 시 URL 업데이트
	const onStatusChange = (value: string) => {
		if (value) {
			searchParams.set('status', value);
		} else {
			searchParams.delete('status');
		}
		setSearchParams(searchParams, { replace: true });
	};

	return (
		<div className='inline-block'>
			<FilterSelect
				label='이슈 필터'
				options={statusOptions}
				value={current}
				onChange={onStatusChange}
				placeholder='필터'
			/>
		</div>
	);
}
