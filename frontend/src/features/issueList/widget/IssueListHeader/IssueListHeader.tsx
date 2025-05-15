import { Checkbox } from '../../shared/CheckBox';

export function IssueListHeader() {
	return (
		<div className='w-full h-16 flex items-center'>
			<div className='flex-none px-8'>
				<Checkbox />
			</div>
		</div>
	);
}
