import React, { useState, useEffect } from 'react';
import styled from 'styled-components';

export function ProgressBar() {
  const [progress, setProgress] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setProgress((prev) => {
        if (prev >= 100) {
          clearInterval(interval);
          return 100;
        }
        return prev + 1;
      });
    }, 40); // 100% / (4000ms / 40ms) = 100 steps

    return () => clearInterval(interval);
  }, []);

  return (
    <ProgressWrapper>
      <Bar $value={progress} />
    </ProgressWrapper>
  );
}

const ProgressWrapper = styled.div`
  width: 100%;
  height: 5px;
  background-color: #e5e7eb;
  border-radius: 3px;
  overflow: hidden;
`;

const Bar = styled.div`
  height: 100%;
  width: ${({ $value }) => $value}%;
  background-color: #d2051d;
  transition: width 0.04s linear;
`;
