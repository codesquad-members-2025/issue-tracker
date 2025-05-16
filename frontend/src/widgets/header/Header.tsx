import { ThemeToggleButton } from '@/shared/theme/ThemeToggleButton';
import { User } from 'lucide-react';
import type { FC } from 'react';

const Header: FC = () => {
	return (
		<div className='flex flex-wrap items-center justify-between h-[94px] gap-4'>
			<div className='flex items-center'>
				<h1 className='text-xl font-semibold'>IssueTracker</h1>
			</div>

			<div className='flex items-center gap-4'>
				<button
					type='button'
					aria-label='프로필'
					className='p-2 rounded-full hover:bg-gray-100 dark:hover:bg-gray-800 transition'
				>
					<User className='w-6 h-6' />
				</button>

				<ThemeToggleButton />
			</div>
		</div>
	);
};

export default Header;
