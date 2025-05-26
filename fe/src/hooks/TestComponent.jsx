import useValidation from './useValidation';

export default function TestComponent({ existedString }) {
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
