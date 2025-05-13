import { Outlet, Navigate } from 'react-router-dom';
import { useUserStore } from '@/stores/userStore';

export default function PrivateRoute() {
  const token = useUserStore((state) => state.accessToken);

  if (!token) {
    return <Navigate to="/login" replace />;
  }
  return <Outlet />;
}
