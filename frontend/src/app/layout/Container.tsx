import type { FC, PropsWithChildren } from 'react';

const Container: FC<PropsWithChildren> = ({ children }) => (
	<div className='w-full max-w-[1440px] px-[clamp(1rem,5vw,80px)] mx-auto'>
		{children}
	</div>
);

export default Container;
