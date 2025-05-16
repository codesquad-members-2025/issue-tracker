import styled from 'styled-components';
import { typography } from '@/styles/foundation';
import { LargeContainerButton } from '../components/ContainerButtons';

const StyledButton = styled(LargeContainerButton)`
  display: flex;
  gap: 8px;
  align-items: center;
`;

export default function SearchButton({ onClick }) {
  const buttonLabel = '검색';
  return <StyledButton onClick={onClick}>{buttonLabel}</StyledButton>;
}
