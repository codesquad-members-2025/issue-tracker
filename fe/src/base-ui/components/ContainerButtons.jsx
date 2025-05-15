import styled from 'styled-components';
import { typography } from '@/styles/foundation';
import { DefaultButton } from './Button';

const ContainerButton = styled(DefaultButton)`
  background-color: ${({ theme }) => theme.brand.surface.default};
  color: ${({ theme }) => theme.brand.text.default};
`;

const LargeContainerButton = styled(ContainerButton)`
  ${typography.available.medium20}
  width:56px;
  height: 240px;
`;
const MediumContainerButton = styled(ContainerButton)`
  ${typography.available.medium16}
  width:48px;
  height: 184px;
`;
const SmallContainerButton = styled(ContainerButton)`
  ${typography.available.medium12}
  width:40px;
  height: 128px;
`;

export { LargeContainerButton, MediumContainerButton, SmallContainerButton };
