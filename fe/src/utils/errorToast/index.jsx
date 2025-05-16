import { useEffect } from 'react';
import styled from 'styled-components';
import { ProgressBar } from './ProgressBar';
import { useErrorStore } from '@/stores/errorStore';

export default function ErrorToast() {
  const clearError = useErrorStore((state) => state.clearError);
  const errorType = useErrorStore((state) => state.type);
  const errorMessage = useErrorStore((state) => state.errorMessage);

  useEffect(() => {
    if (!errorType) return; //errorType 이 null 일 경우 종료.
    const timer = setTimeout(() => {
      clearError();
    }, 4000);

    return () => clearTimeout(timer);
  }, [errorType]);

  return (
    <>
      {errorType && (
        <ToastWrapper>
          <ContentsWrapper>
            <ToastTitle>{`에러 발생 ErrorType:${errorType}`}</ToastTitle>
            <ToastDescription>{errorMessage}</ToastDescription>
          </ContentsWrapper>
          <ProgressBar />
        </ToastWrapper>
      )}
    </>
  );
}

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
