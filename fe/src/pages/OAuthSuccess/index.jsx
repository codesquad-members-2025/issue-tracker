import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSearchParams } from 'react-router-dom';
import { useAuthStore } from '@/stores/authStore';

export default function OAuthSuccess() {
  const setUser = useAuthStore((s) => s.setUser);
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  useEffect(() => {
    const token = searchParams.get('token'); // 토큰 추출
    const { loginId, profileImageUrl } = tokenDecoder(token);
    setUser(loginId, profileImageUrl, token);
    navigate('/');
  }, []);
}
