import { type IssueStatus } from '@/features/issue/types/issue';

interface CreatedMessageParams {
  createdAt: string;
  author: string;
  isClosed: boolean;
}

/**
 * 이슈의 생성 시간과 작성자 이름, 상태를 받아
 * "몇 분/시간/일 전, 누가 열었는지/닫았는지" 형태의 문자열을 반환합니다.
 *
 * @param {string} createdAt - 이슈 생성 시간 (ISO 8601 문자열)
 * @param {string} author - 작성자 이름
 * @param {boolean} isClosed - 이슈 상태 (true: 닫힘, false: 열림)
 * @returns {string} 메시지 문자열
 *
 * @example
 * // '3시간 전, 홍길동님에 의해 열렸습니다.'
 * formatCreatedMessage('2025-05-13T11:00:00Z', '홍길동', false);
 */
export function formatCreatedMessage({
  createdAt,
  author,
  isClosed,
}: CreatedMessageParams) {
  const now = new Date();
  const created = new Date(createdAt);
  const diffMs = now.getTime() - created.getTime();
  const diffMinutes = Math.floor(diffMs / 1000 / 60);

  let timeString = '';

  if (diffMinutes < 1) {
    timeString = '방금';
  } else if (diffMinutes < 60) {
    timeString = `${diffMinutes}분 전`;
  } else if (diffMinutes < 60 * 24) {
    const diffHours = Math.floor(diffMinutes / 60);
    timeString = `${diffHours}시간 전`;
  } else {
    const diffDays = Math.floor(diffMinutes / 60 / 24);
    timeString = `${diffDays}일 전`;
  }

  const statusText = isClosed ? '닫혔습니다' : '열렸습니다';

  return `이 이슈가 ${timeString}에 ${author}님에 의해 ${statusText}`;
}

//TODO 필터 상태에 따라 확장
/**
 * 이슈 상태 값을 기반으로 GitHub 스타일의 쿼리 문자열을 생성합니다.
 * 상태 값이 주어지지 않으면 기본적으로 모든 이슈를 의미하는 'is:issue'를 반환합니다.
 *
 * @param {IssueStatus} [state] - 이슈의 상태값 ('open' 또는 'closed' 등). 선택값입니다.
 *                                상태가 없을 경우 기본 쿼리('is:issue')를 반환합니다.
 * @returns {string} 필터링 조건에 맞는 쿼리 문자열
 *
 * @example
 * buildIssueQuery(); // 'is:issue'
 * buildIssueQuery('open'); // 'is:issue state:open'
 */
export function buildIssueQuery(state?: IssueStatus): string {
  if (!state) return 'is:issue';
  return `is:issue state:${state}`;
}
