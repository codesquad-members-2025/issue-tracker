import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { radius } from '@/styles/foundation';
import { typography } from '@/styles/foundation';
import { SmallOutlineButton } from '../components/OutlineButtons';
import { SmallContainerButton } from '../components/ContainerButtons';
import formatDateInput from './formatDateInput';
import useValidation from '@/hooks/useValidation';

export default function MilestoneCreateForm({
  isAdd,
  name = '',
  endDate = '',
  description = '',
  onCancel,
  onSubmit,
}) {
  const [nameValue, setNameValue] = useState('');
  const [dateValue, setDateValue] = useState('');
  const [descriptionValue, setDescriptionValue] = useState('');
  const { isValid, setCurrentInput } = useValidation({ existedString: nameValue });

  const handleNameChange = (e) => {
    setNameValue(e.target.value);
    setCurrentInput(e.target.value);
  };

  const handleDateChange = (e) => {
    const formatted = formatDateInput(e.target.value);
    setDateValue(formatted);
  };

  const handleDescriptionChange = (e) => {
    setDescriptionValue(e.target.value);
  };

  useEffect(() => {
    setNameValue(name);
    setDateValue(endDate);
    setDescriptionValue(description);
  }, []);

  return (
    <Wrapper $isAdd={isAdd}>
      <Title>새로운 마일스톤 추가</Title>
      <InputContainer>
        <InputWrapper>
          <Label>이름</Label>
          <Input
            placeholder="마일스톤의 이름을 입력하세요."
            value={nameValue}
            onChange={handleNameChange}
          />
        </InputWrapper>

        <InputWrapper>
          <Label>{`완료일(선택)`}</Label>
          <Input placeholder="YYYY.MM.DD" value={dateValue} onChange={handleDateChange} />
        </InputWrapper>

        <InputWrapper>
          <Label>{`설명(선택)`}</Label>
          <Input
            placeholder="마일스톤에 대한 설명을 입력하세요."
            value={descriptionValue}
            onChange={handleDescriptionChange}
          />
        </InputWrapper>
      </InputContainer>
      <Buttonwrapper>
        <SmallOutlineButton onClick={onCancel}>
          <svg
            width="17"
            height="16"
            viewBox="0 0 17 16"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M11.7999 4.70026L5.20025 11.2999M5.2002 4.7002L11.7999 11.2999"
              stroke="currentColor"
              stroke-width="1.6"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
          </svg>
          <span>취소</span>
        </SmallOutlineButton>
      </Buttonwrapper>
      <SmallContainerButton
        onClick={() =>
          onSubmit({ name: nameValue, endDate: dateValue, description: descriptionValue })
        }
        disabled={!isValid}
      >
        <svg
          width="16"
          height="16"
          viewBox="0 0 17 16"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M8.5 3.3335V12.6668"
            stroke="currentColor"
            stroke-width="1.6"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
          <path
            d="M3.8335 8H13.1668"
            stroke="currentColor"
            stroke-width="1.6"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
        <span>완료</span>
      </SmallContainerButton>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  width: 100%;
  border-radius: ${radius.large};
  border: ${({ $isAdd, theme }) => ($isAdd ? `1px solid ${theme.border.default}` : null)};
  background: ${({ theme }) => theme.surface.strong};
  padding: 36px 32px 32px 32px;
  display: flex;
  flex-direction: column;
  gap: 24px;
`;

const Title = styled.h2`
  ${typography.display.bold20}
  color:${({ theme }) => theme.text.strong};
`;

const Label = styled.label`
  ${typography.display.medium12};
  width: 64px;
  color: ${({ theme }) => theme.text.weak};
`;

const Input = styled.input`
  ${typography.display.medium16};
  color: ${({ theme }) => theme.text.default};
  background: transparent;
  outline: none;
`;

const InputContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
`;

const InputWrapper = styled.div`
  display: flex;
  gap: 8px;
  align-items: center;
  padding: 8px 16px;
`;

const Buttonwrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
`;
