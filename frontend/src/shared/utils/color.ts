/**
 * 배경색(hex 코드)을 기반으로 가독성 높은 텍스트 색상과
 * 너무 밝은 배경일 경우 테두리 색상을 함께 반환합니다.
 *
 * @param {string} bgColor - 라벨의 배경색 (예: '#FFFFFF', '#333', '#AABBCC')
 * @returns {{ textColor: string; borderColor: string }} 텍스트 색상과 테두리 색상
 *
 * @example
 * // 텍스트 색상은 흰색, 테두리는 transparent
 * getAccessibleLabelStyle('#333333');
 *
 * // 텍스트 색상은 검정색, 테두리는 연한 회색
 * getAccessibleLabelStyle('#FFFFFF');
 */
export function getAccessibleLabelStyle(bgColor: string) {
  const hex = bgColor.replace('#', '');
  const normalizedHex =
    hex.length === 3
      ? hex
          .split('')
          .map(c => c + c)
          .join('')
      : hex;

  const r = parseInt(normalizedHex.substring(0, 2), 16);
  const g = parseInt(normalizedHex.substring(2, 4), 16);
  const b = parseInt(normalizedHex.substring(4, 6), 16);

  const luminance = (0.299 * r + 0.587 * g + 0.114 * b) / 255;
  const textColor = luminance > 0.6 ? '#000000' : '#FFFFFF';
  const needsBorder = luminance > 0.9;

  return {
    textColor,
    borderColor: needsBorder ? '#D9D9D9' : 'transparent',
  };
}
