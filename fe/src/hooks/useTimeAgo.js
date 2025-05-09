import { useState, useEffect, use } from 'react';

export default function useTimeAgo(inputTime) {
  const [gapTime, setGapTime] = useState(() => getTimeDifferenceLabel(inputTime)); //함수 지연 실행 (성능 향상 효과)
  useEffect(() => {
    const interval = setInterval(() => {
      setGapTime(getTimeDifferenceLabel(inputTime));
    }, 60000);
    return () => clearInterval(interval);
  }, [inputTime]);
  return gapTime;
}

function getTimeDifferenceLabel(inputTime) {
  const now = new Date();
  const target = new Date(inputTime);
  const diffInSeconds = Math.floor((now - target) / 1000);

  const minute = 60;
  const hour = 60 * minute;
  const day = 24 * hour;
  const week = 7 * day;
  const month = 30 * day;
  const year = 365 * day;

  if (diffInSeconds < minute) {
    return '1 minute'; // 항상 1 minute로 고정
  }

  if (diffInSeconds < hour) {
    const minutes = Math.floor(diffInSeconds / minute);
    return `${minutes} ${minutes === 1 ? 'minute' : 'minutes'}`;
  }

  if (diffInSeconds < day) {
    const hours = Math.floor(diffInSeconds / hour);
    return `${hours} ${hours === 1 ? 'hour' : 'hours'}`;
  }

  if (diffInSeconds < week) {
    const days = Math.floor(diffInSeconds / day);
    return `${days} ${days === 1 ? 'day' : 'days'}`;
  }

  if (diffInSeconds < month) {
    const weeks = Math.floor(diffInSeconds / week);
    return `${weeks} ${weeks === 1 ? 'week' : 'weeks'}`;
  }

  if (diffInSeconds < year) {
    const months = Math.floor(diffInSeconds / month);
    return `${months} ${months === 1 ? 'month' : 'months'}`;
  }

  const years = Math.floor(diffInSeconds / year);
  return `${years} ${years === 1 ? 'year' : 'years'}`;
}
