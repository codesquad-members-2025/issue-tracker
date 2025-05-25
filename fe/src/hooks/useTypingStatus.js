/* 
목표 -> 사용자의 입력 액션이 실행중에는 false 반환, 사용자의 액션이 멈추면 true 반환후에 2초뒤에 다시 false 반환.
설계
-> 사용자의 onChange 이벤트가 실행되면 즉시 false 반환.
-> 이벤트가 없으면 바로 true 반환 ----> 2초뒤에 false 반환

입력 -> onChange 발생시 
*/
import { useState, useEffect } from 'react';

export default function useTypingStatus(value) {
  const [active, setActive] = useState(false);

  useEffect(() => {
    setActive(true);
    const timer = setTimeout(() => {
      setActive(false);
    }, 2000);
    return () => {
      clearTimeout(timer);
    };
  }, [value]);
  return active;
}
