import { API } from '@/shared/constants/api';
import { type IssuesResponse } from '../types/issue';

export async function getIssues(filterQuery: string): Promise<IssuesResponse> {
  const url = new URL(API.ISSUES, window.location.origin);
  url.searchParams.set('q', filterQuery);

  const res = await fetch(url.toString(), {
    credentials: 'include',
  });

  switch (res.status) {
    case 200:
      return res.json();

    case 400:
      throw new Error('잘못된 요청입니다 (query string 오류)');

    case 401:
      // window.location.href = '/login'; //TODO 로그인 기능 구현시 수정
      throw new Error('로그인이 필요합니다');

    case 404:
      throw new Error('요청한 경로가 존재하지 않습니다');

    case 500:
      throw new Error('서버 오류가 발생했습니다');

    default:
      throw new Error(`예상치 못한 오류 (status: ${res.status})`);
  }
}
