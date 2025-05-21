/**
 * 생성 시간을 받아 "몇 분/시간/일 전" 형식의 문자열을 반환합니다.
 *
 * @param {string} createdAt - ISO 8601 형식의 시간 문자열
 * @returns {string} 경과 시간 기반 문자열
 *
 * @example
 * getTimeAgoString('2025-05-20T14:00:00Z'); // '2시간 전'
 */
export function getTimeAgoString(createdAt: string): string {
  const now = new Date();
  const created = new Date(createdAt);
  const diffMs = now.getTime() - created.getTime();
  const diffMinutes = Math.floor(diffMs / 1000 / 60);

  if (diffMinutes < 1) return '방금 전';
  if (diffMinutes < 60) return `${diffMinutes}분 전`;
  if (diffMinutes < 60 * 24) return `${Math.floor(diffMinutes / 60)}시간 전`;
  return `${Math.floor(diffMinutes / 60 / 24)}일 전`;
}
