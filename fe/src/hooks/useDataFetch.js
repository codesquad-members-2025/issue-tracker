/*
설계 목표: 데이터 패치 관련 커스텀 훅 만들기 -> 에러핸들링, 로딩, 데이터 처리 기능 구현 목표
API를 호출해서 데이터를 가져오는 커스텀 훅을 만든다.
서버와 통신하는 동안 로딩 상태와 에러 상태를 관리한다.

1. 입력 : api, fetch 옵션 객체
2. 반환 : 서버 응답값(response), isLoading, error 데이터 통신 상태를 반환한다.
*/

import { useState, useEffect } from 'react';

export default function useDataFetch({ apiUrl, opionObj }) {
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [response, setResponse] = useState(null);

  useEffect(() => {
    async function dataFetch() {
      try {
        const res = await fetch(apiUrl, opionObj);
        if (!res.ok) {
          throw new Error(`데이터 통신 오류_에러코드:${res.status}`);
        }
        const data = await res.json();
        setResponse(data);
      } catch (err) {
        setError(err);
      } finally {
        setIsLoading(false);
      }
    }
    dataFetch();
  }, [apiUrl, opionObj]);

  return { response, error, isLoading };
}
