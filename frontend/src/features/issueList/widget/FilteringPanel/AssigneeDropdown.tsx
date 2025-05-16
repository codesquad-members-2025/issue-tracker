import { useUserList } from '@/entities/user/useUserList';
// src/entities/user/ui/AssigneeDropdown.tsx
import {
	CustomDropdownPanel,
	type DropdownOption,
} from '@/shared/ui/CustomDropdownPanel';
import { useMemo, useState } from 'react';

export default function AssigneeDropdown() {
	const [selected, setSelected] = useState<string | null>(null);
	const { data, isLoading, error } = useUserList();

	// ✅ useMemo를 항상 호출하도록 최상단에 선언
	const userOptions = useMemo<DropdownOption[]>(() => {
		const noneOption: DropdownOption = {
			id: 0,
			value: 'none',
			display: '담당자가 없는 이슈',
		};

		const fetchedOptions: DropdownOption[] =
			data?.users.map((user) => ({
				id: user.id,
				value: user.username,
				display: user.username,
				imageUrl: user.imageUrl,
			})) ?? [];

		return [noneOption, ...fetchedOptions];
	}, [data]);

	// 로딩·에러 UI는 그 다음에 처리
	if (isLoading) {
		return <div>담당자 목록 로딩 중…</div>;
	}
	if (error) {
		return (
			<div className='text-destructive'>
				담당자 목록을 불러오는 중 에러가 발생했습니다.
			</div>
		);
	}

	return (
		<div className='flex justify-center'>
			<CustomDropdownPanel
				label='담당자'
				panelLabel='담당자 필터'
				options={userOptions}
				value={selected}
				onChange={setSelected}
			/>
		</div>
	);
}
