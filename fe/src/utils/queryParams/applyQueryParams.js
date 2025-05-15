import { useSearchParams, useNavigate } from 'react-router-dom';

/**
 * 선택한 필터 객체를 받아 기존 쿼리파람을 무시하고 새 쿼리로 덮어씌움
 *
 * @example
 * applyQueryParams({ author: 'me', label: 'bug' });
 */
export function useApplyQueryParams() {
  const [, setSearchParams] = useSearchParams();
  const navigate = useNavigate();

  /**
   * @param {Object} newQueryObj - { author: 'me', label: 'bug' }
   */
  const applyQueryParams = (newQueryObj) => {
    const newParams = new URLSearchParams();

    Object.entries(newQueryObj).forEach(([key, value]) => {
      if (Array.isArray(value)) {
        value.forEach((v) => newParams.append(key, v));
      } else {
        newParams.set(key, value);
      }
    });

    // 새 쿼리 파라미터로 완전히 덮어쓰기
    navigate({ search: `?${newParams.toString()}` }, { replace: true });
  };

  return applyQueryParams;
}
