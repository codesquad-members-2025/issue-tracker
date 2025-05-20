import styled from '@emotion/styled';
import { useNavigate } from 'react-router-dom';
import PlusIcon from '@/assets/icons/plus.svg?react';

export default function CreateIssueButton() {
  const navigate = useNavigate();

  return (
    <StyledButton onClick={() => navigate('/issues/new')}>
      <PlusIcon />
      <StyledLabel>이슈 작성</StyledLabel>
    </StyledButton>
  );
}

const StyledButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 128px;
  padding: 12px 0;
  background-color: ${({ theme }) => theme.palette.blue};
  border-radius: ${({ theme }) => theme.radius.medium};
  color: #ffffff;

  cursor: pointer;
  transition: all 0.2s ease;

  &:hover {
    opacity: 0.8;
  }
`;

const StyledLabel = styled.span`
  padding: 0 4px;
  ${({ theme }) => theme.typography.availableMedium12};
`;
