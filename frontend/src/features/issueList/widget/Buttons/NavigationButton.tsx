import LabelIcon from '@/assets/label.svg?react';
import MilestonIcon from '@/assets/milestone.svg?react';
import { Button } from '@/shared/ui/button';
import { useNavigate } from 'react-router-dom';

export function LabelListButton() {
	const navigate = useNavigate();
	const handleClick = () => {
		navigate('/labels');
	};

	return (
		<Button
			variant='ghost'
			size='sm'
			pattern='icon-text'
			onClick={handleClick}
			className='text=[var(--neutral-text-default)] font-available-medium-12 rounded-r-none'
		>
			<LabelIcon />
			<span>레이블 목록</span>
		</Button>
	);
}

export function MilestoneListButton() {
	const navigate = useNavigate();
	const handleClick = () => {
		navigate('/milestones');
	};

	return (
		<Button
			variant='ghost'
			size='sm'
			pattern='icon-text'
			onClick={handleClick}
			className=' text=[var(--neutral-text-default)] font-available-medium-12 rounded-l-none'
		>
			<MilestonIcon />
			<span>마일스톤 목록</span>
		</Button>
	);
}
