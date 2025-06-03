import { useAuthStore } from "@/stores/useAuthStore";
import { apiFetch } from "@/hooks/useApiLoginFetch";

export const LogoutButton = () => {
  const logout = useAuthStore((s) => s.logout);
  return (
    <button
      type="button"
      onClick={async () => {
        await apiFetch("/auth/logout", { method: "POST" }); // 서버에서 refresh 쿠키 삭제
        logout(); // 전역 상태 초기화
        location.href = "/login"; // 이동
      }}
    >
      로그아웃
    </button>
  );
};
