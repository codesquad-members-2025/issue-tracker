import PlusIcon from '@/assets/plus.svg?react';
import { Button } from '@/shared/ui/button';
import { useNavigate } from 'react-router-dom';

export function IssueCreationButton() {
	const navigate = useNavigate();

	const handleClick = () => {
		navigate('/issues/new');
	};

	return (
		<Button
			variant='contained'
			size='sm'
			pattern='icon-text'
			onClick={handleClick}
			className='font-available-medium-12'
		>
			<PlusIcon className='size-4' />
			<span>이슈 작성</span>
		</Button>
	);
}
