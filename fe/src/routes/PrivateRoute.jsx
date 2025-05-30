import { Outlet, Navigate } from 'react-router-dom';
import { useAuthStore } from '@/stores/authStore';

export default function PrivateRoute() {
  /*

  const token = useAuthStore((state) => state.accessToken); // -> 새로고침시 토근 사라져서 계속해서 로그인해야하는 문제 발생!
  */
  const token = localStorage.getItem('token');

  if (!token) {
    return <Navigate to="/login" replace />;
  }
  return <Outlet />;
}
