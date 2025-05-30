/*
로그인 & 회원가입시 사용되는 유틸컴포넌트입니다.
mainButtonLabel = 로그인 or 회원가입 라벨 입력
onSubmit = 서버에 제출하는 핸들러 함수 입력
err = 에러발생시 prop으로 에러문구 전달
*/

import React, { useState } from 'react';
import styled from 'styled-components';
import { typography } from '@/styles/foundation';
import { useErrorStore } from '@/stores/errorStore';

const StyledForm = styled.form`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

const StyledInput = styled.input`
  ${typography.display.medium16};
  color: ${({ theme }) => theme.text.default};
  background-color: ${({ theme }) => theme.surface.bold};
  width: 320px;
  padding: 16px;
  border-radius: 16px;
  &:focus {
    outline: none;
    background-color: ${({ theme }) => theme.surface.strong};
    border-color: ${({ theme }) => theme.border.active};
  }
`;

const MainButton = styled.button`
  ${typography.available.medium20};
  width: 320px;
  height: 56px;
  background-color: ${({ theme }) => theme.brand.surface.default};
  color: ${({ theme }) => theme.brand.text.default};
  border: none;
  border-radius: 16px;
  cursor: pointer;
  &:hover {
    background-color: #2563eb;
  }
  &:disabled {
    opacity: 0.32;
    cursor: not-allowed;
  }
`;

export default function InputForm({
  setId,
  setPw,
  mainButtonLabel,
  onSubmit,
  isDisabled,
  error = null,
}) {
  const errorType = useErrorStore((state) => state.type);
  return (
    <>
      <StyledForm onSubmit={onSubmit}>
        <div>
          <StyledInput
            id="Id"
            type="text"
            placeholder="아이디"
            onChange={(e) => setId(e.target.value)}
            aria-describedby="Id-description"
            aria-invalid={!!error}
          />
        </div>

        <StyledInput
          id="pw"
          type="password"
          placeholder="비밀번호"
          onChange={(e) => setPw(e.target.value)}
          aria-describedby="pw-description"
        />

        <MainButton type="submit" disabled={isDisabled || errorType}>
          {mainButtonLabel}
        </MainButton>
      </StyledForm>
    </>
  );
}
