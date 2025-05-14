const API_VERSION = '/api/v1';
const API_HOST = import.meta.env.VITE_API_HOST; // 환경변수로 도메인 관리

export const API_BASE_URL = `${API_HOST}${API_VERSION}`;

export const API = {
  ISSUES: `${API_BASE_URL}/issues`,
  LABELS: `${API_BASE_URL}/labels`,
};
