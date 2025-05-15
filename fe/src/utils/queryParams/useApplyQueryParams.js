import { useNavigate } from 'react-router-dom';

/**
 * 사용자가 선택한 필터 객체를 입력받아 URL 쿼리파람을 완전히 덮어씌움
 */
export function useApplyQueryParams() {
  const navigate = useNavigate();

  return (filterObject) => {
    const params = new URLSearchParams();

    Object.entries(filterObject).forEach(([key, value]) => {
      if (Array.isArray(value)) {
        value.forEach((v) => params.append(key, v));
      } else {
        params.set(key, value);
      }
    });

    navigate({ search: `?${params.toString()}` }, { replace: true });
  };
}
