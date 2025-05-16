// src/shared/auth/AuthGuard.tsx
import type { ReactNode } from 'react';
import { Navigate, useLocation } from 'react-router-dom';

interface AuthGuardProps {
	children: ReactNode;
}

const AuthGuard = ({ children }: AuthGuardProps) => {
	const location = useLocation();

	// 임시 인증 플래그: sessionStorage에 'isAuth'가 'true'여야 인증된 것으로 간주
	const isAuth = sessionStorage.getItem('isAuth') === 'true';

	// 렌더 단계에서 바로 리다이렉트
	if (!isAuth) {
		return <Navigate to='/login' state={{ from: location }} replace />;
	}

	// 인증됐으면 자식 렌더
	return <>{children}</>;
};

export default AuthGuard;
