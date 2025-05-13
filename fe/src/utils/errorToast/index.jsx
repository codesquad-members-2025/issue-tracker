import { useState } from 'react';
import styled from 'styled-components';
import { ProgressBar } from './ProgressBar';

export default function ErrorToast() {
  const [isVisible, setIsVisible] = useState(false);

  function showToast() {
    setIsVisible(true);
    setTimeout(() => setIsVisible(false), 4000);
  }

  return (
    <>
      <StyledButton onClick={showToast}>Show Toast</StyledButton>
      {isVisible && (
        <ToastWrapper>
          <ContentsWrapper>
            <ToastTitle>오류 발생</ToastTitle>
            <ToastDescription>요청 처리에 실패했습니다.</ToastDescription>
          </ContentsWrapper>
          <ProgressBar />
        </ToastWrapper>
      )}
    </>
  );
}

const StyledButton = styled.button`
  padding: 8px 16px;
  background: white;
  border: 1px solid #ccc;
  border-radius: 4px;
  cursor: pointer;

  &:hover {
    background: #f2f2f2;
  }
`;

const ToastWrapper = styled.div`
  position: fixed;
  bottom: 20px;
  right: 20px;
  background-color: #ffe5e5;

  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 16px;
  min-width: 300px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
`;

const ToastTitle = styled.div`
  font-weight: bold;
  margin-bottom: 4px;
`;

const ToastDescription = styled.div`
  font-size: 14px;
  color: #333;
`;

const ContentsWrapper = styled.div`
  display: flex;
  flex-direction: column;
  padding: 16px 20px;
`;
