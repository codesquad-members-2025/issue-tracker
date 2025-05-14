import { ThemeToggleButton } from '@/shared/theme/ThemeToggleButton';
import type { FC } from 'react';

const Header: FC = () => {
	return (
		<div>
			<h1>헤더</h1>
			<ThemeToggleButton />
			{/* TODO: 헤더 컴포넌트 구현 */}
		</div>
	);
};

export default Header;
