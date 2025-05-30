import styled from 'styled-components';
import { typography } from '@/styles/foundation';

const Container = styled.div`
  ${typography.display.medium16}
  color:${({ theme }) => theme.text.weak};
  margin-top: 14px;
  margin-bottom: 18px;
`;

export default function OrLabel() {
  const textLabel = 'or';
  return <Container>{textLabel}</Container>;
}
