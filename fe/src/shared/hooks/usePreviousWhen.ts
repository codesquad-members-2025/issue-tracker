import { useEffect, useRef } from 'react';

export default function usePreviousWhen<T>(value: T, condition: boolean): T {
  const ref = useRef(value);
  useEffect(() => {
    if (condition) {
      ref.current = value;
    }
  }, [value, condition]);
  return ref.current;
}
