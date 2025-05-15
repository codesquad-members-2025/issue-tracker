import {
	CustomDropdownPanel,
	type DropdownOption,
} from '@/shared/ui/CustomDropdownPanel';
import { useState } from 'react';

const userOptions: DropdownOption[] = [
	{ id: 0, value: 'none', display: '담당자가 없는 이슈' },
	{
		id: 1,
		value: 'jjinbbangS2',
		display: 'jjinbbangS2',
		imageUrl: 'https://user-images.githubusercontent.com/1.png',
	},
	{
		id: 2,
		value: 'htmlH3AD',
		display: 'htmlH3AD',
		imageUrl: 'https://user-images.githubusercontent.com/2.png',
	},
	{
		id: 3,
		value: 'samsamis9',
		display: 'samsamis9',
		imageUrl: 'https://user-images.githubusercontent.com/3.png',
	},
	{
		id: 4,
		value: 'dev_angel0',
		display: 'dev_angel0',
		imageUrl: 'https://user-images.githubusercontent.com/4.png',
	},
];

export default function ExampleAssigneeDropdown() {
	const [selected, setSelected] = useState<string | null>(null);

	return (
		<div className='flex justify-center'>
			<CustomDropdownPanel
				label='담당자'
				panelLabel='담당자 필터'
				options={userOptions}
				value={selected}
				onChange={setSelected}
			/>
			{/* 아래는 상태 확인용 (선택된 담당자 보여주기) */}
			{/* <div className='ml-6 flex items-center text-sm'>
				{selected ? (
					<>
						선택된 담당자: <span className='font-bold'>{selected}</span>
					</>
				) : (
					'담당자가 없는 이슈만 보기'
				)}
			</div> */}
		</div>
	);
}
