/*
- 사용자의 입력값이 유효한지(1글자 이상)를 검사하는 커스텀 훅 입니다.
- 사용자가 입력한 글자수가 1글자 이상이면 true / 0글자인 경우에 false 를 반환합니다.

사용방법
- 매개변수 없이 선언을 하면 -> isValid, setString 을 반환합니다.
* isValid -> 현재 입력값이 유효한지 판단합니다.
* setInput -> 입력 받는 문자열을 해당 함수에 입력합니다.

* 범용성 확장 
-> 입력 값으로 현재 필드 값을 받는다. 그리고 입력 받은 필드값과 값을 비교해서 달라지면 true, 달라진게 없으면 false 반환.
-> 만약 첫 문자가 공백이여도 false 를 반환한다.
*/

import { useState, useEffect } from 'react';

export default function useValidation({ existedString }) {
  const [isValid, setIsValid] = useState(false);
  const [currentInput, setCurrentInput] = useState('');

  useEffect(() => {
    if (existedString === currentInput) {
      setIsValid(false);
    } else if (currentInpu.trim() === '') {
      setIsValid(false);
    } else {
      setIsValid(true);
    }
  }, [currentInput]);

  useEffect(() => {
    //초기 입력값 세팅
    setCurrentInput(existedString);
  }, [existedString]);
  return { isValid, setCurrentInput, currentInput };
}
