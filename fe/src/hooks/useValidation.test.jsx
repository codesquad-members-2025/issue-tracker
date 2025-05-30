import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import React from 'react';
import userEvent from '@testing-library/user-event';
import useValidation from './useValidation';

function TestComponent({ existedString }) {
  // 실제 훅 사용
  const { isValid, currentInput, setCurrentInput } = useValidation({ existedString });

  return (
    <div>
      <input
        data-testid="test-input"
        value={currentInput}
        onChange={(e) => setCurrentInput(e.target.value)}
      />
      <div data-testid="result">{isValid ? 'valid' : 'invalid'}</div>
    </div>
  );
}

describe('useValidation 테스트 코드', () => {
  it('초기값은 existedString이어야 한다', () => {
    render(<TestComponent existedString="hello" />);
    const input = screen.getByTestId('test-input');
    expect(input.value).toBe('hello');
    const result = screen.getByTestId('result');
    expect(result.textContent).toBe('invalid');
  });

  it('입력값이 existedString과 같으면 invalid', async () => {
    render(<TestComponent existedString="foo" />);
    const input = screen.getByTestId('test-input');
    await userEvent.clear(input);
    await userEvent.type(input, 'foo');
    const result = screen.getByTestId('result');
    expect(result.textContent).toBe('invalid');
  });

  it('입력값이 existedString과 다르면 valid', async () => {
    render(<TestComponent existedString="foo" />);
    const input = screen.getByTestId('test-input');
    await userEvent.clear(input);
    await userEvent.type(input, 'bar');
    const result = screen.getByTestId('result');
    expect(result.textContent).toBe('valid');
  });

  it('입력값이 빈 문자열이면 invalid', async () => {
    render(<TestComponent existedString="foo" />);
    const input = screen.getByTestId('test-input');
    await userEvent.clear(input);
    const result = screen.getByTestId('result');
    expect(result.textContent).toBe('invalid');
  });

  it('입력값이 공백만 있으면 invalid', async () => {
    render(<TestComponent existedString="foo" />);
    const input = screen.getByTestId('test-input');
    await userEvent.clear(input);
    await userEvent.type(input, '   ');
    const result = screen.getByTestId('result');
    expect(result.textContent).toBe('invalid');
  });
});
