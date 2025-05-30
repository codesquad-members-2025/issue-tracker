import styled from 'styled-components';
import { typography } from '@/styles/foundation';
import { DefaultButton } from './Button';

const OutlineButton = styled(DefaultButton)`
  align-items: center;
  background-color: transparent;
  color: ${({ theme }) => theme.brand.text.weak};
  border: 1px solid ${({ theme }) => theme.border.default};
`;

const LargeOutlineButton = styled(OutlineButton)`
  ${typography.available.medium20}
  width:240px;
  height: 56px;
`;
const MediumOutlineButton = styled(OutlineButton)`
  ${typography.available.medium16}
  width:184px;
  height: 48px;
`;
const SmallOutlineButton = styled(OutlineButton)`
  ${typography.available.medium12}
  width:128px;
  height: 40px;
`;

export { LargeOutlineButton, MediumOutlineButton, SmallOutlineButton };
