import { Button } from '@/shared/ui/button';
// src/pages/LoginPage.tsx
import type { FC } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const LoginPage: FC = () => {
	const navigate = useNavigate();
	const location = useLocation();
	// 이전에 보호된 페이지에서 이동했다면 그 경로로, 아니면 기본 /issues
	const from =
		(location.state as { from?: { pathname: string } })?.from?.pathname ??
		'/issues';

	const handleLogin = () => {
		// 임시 인증 플래그 설정
		sessionStorage.setItem('isAuth', 'true');
		// 로그인 후 원래 페이지로 이동
		navigate(from, { replace: true });
	};

	return (
		<div className='flex flex-col items-center justify-center min-h-screen p-4'>
			{/* 실제 폼 대신 버튼으로 간단 테스트 */}
			<Button onClick={handleLogin}>로그인</Button>
		</div>
	);
};

export default LoginPage;
