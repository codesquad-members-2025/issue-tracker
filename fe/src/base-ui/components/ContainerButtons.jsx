import styled from 'styled-components';
import { typography } from '@/styles/foundation';
import { DefaultButton } from './Button';

const ContainerButton = styled(DefaultButton)`
  background-color: ${({ theme }) => theme.brand.surface.default};
  color: ${({ theme }) => theme.brand.text.default};
`;

const LargeContainerButton = styled(ContainerButton)`
  ${typography.available.medium20}
  width:240px;
  height: 56px;
`;
const MediumContainerButton = styled(ContainerButton)`
  ${typography.available.medium16}
  width:184px;
  height: 48px;
`;
const SmallContainerButton = styled(ContainerButton)`
  ${typography.available.medium12}
  width:128px;
  height: 40px;
`;

export { LargeContainerButton, MediumContainerButton, SmallContainerButton };
