export default function getOptionWithToken(options, freshToken = null) {
  let token = freshToken;
  if (!token) {
    token = localStorage.getItem('token'); // 토큰 자동 조회
  }
  const headers = {
    ...options.headers,
    Authorization: `Bearer ${token}`,
  };
  const option = { ...options, headers, credentials: 'include' };
  return option;
}
