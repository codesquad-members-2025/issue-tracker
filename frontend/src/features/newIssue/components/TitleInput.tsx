import styled from '@emotion/styled';
import { useState } from 'react';

interface TitleInputProps {
  value: string;
  onChange: (value: string) => void;
  label: string;
}

export default function TitleInput({
  value,
  onChange,
  label,
}: TitleInputProps) {
  const [isFocused, setIsFocused] = useState(false);

  return (
    <Wrapper>
      <LabelAbove isFloating={isFocused || !!value}>{label}</LabelAbove>
      <StyledInput
        value={value}
        onChange={e => onChange(e.target.value)}
        onFocus={() => setIsFocused(true)}
        onBlur={() => setIsFocused(false)}
      />
    </Wrapper>
  );
}

const Wrapper = styled.div`
  position: relative;
  width: 100%;
  height: 56px;
  border-radius: ${({ theme }) => theme.radius.medium};
  background-color: ${({ theme }) => theme.neutral.surface.bold};
`;

const LabelAbove = styled.label<{ isFloating: boolean }>`
  position: absolute;
  left: 16px;
  top: ${({ isFloating }) => (isFloating ? '8px' : '50%')};
  transform: ${({ isFloating }) => (isFloating ? 'none' : 'translateY(-50%)')};
  ${({ theme, isFloating }) =>
    isFloating
      ? theme.typography.displayMedium12
      : theme.typography.displayMedium16};
  color: ${({ theme }) => theme.neutral.text.weak};
  transition: all 0.2s ease;
`;

const StyledInput = styled.input`
  all: unset;
  width: 100%;
  height: 100%;
  padding: 24px 16px 8px;
  box-sizing: border-box;
  color: ${({ theme }) => theme.neutral.text.strong};
  ${({ theme }) => theme.typography.displayMedium16};

  &::placeholder {
    color: transparent;
  }
`;
