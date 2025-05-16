import styled from 'styled-components';
import { typography } from '@/styles/foundation';

export const Container = styled.div`
  position: relative;
  display: inline-block;
`;

export const TriggerButton = styled.button`
  ${typography.available.medium16}
  width:80px;
  height: 32px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 4px 0px;
  background: transparent;
  cursor: pointer;
  color: ${({ theme }) => theme.text.default};

  &:hover {
    opacity: 0.8;
  }
`;

export const Menu = styled.div`
  position: absolute;
  top: 100%;
  left: 0;
  width: ${({ $width }) => $width || '12rem'};
  background: white;
  border: 1px solid #ccc;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 10;
  padding: 0.5rem 0;
`;

export const Label = styled.div`
  padding: 0.5rem 1rem;
  font-weight: bold;
  font-size: 0.875rem;
`;

export const Separator = styled.div`
  height: 1px;
  background: #e5e5e5;
  margin: 4px 0;
`;

export const Group = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Item = styled.div`
  padding: 0.5rem 1rem;
  font-size: 0.875rem;
  cursor: ${({ disabled }) => (disabled ? 'not-allowed' : 'pointer')};
  opacity: ${({ disabled }) => (disabled ? 0.4 : 1)};
  display: flex;
  justify-content: space-between;

  &:hover {
    background: ${({ disabled }) => (disabled ? 'none' : '#f5f5f5')};
  }
`;

export const Shortcut = styled.span`
  opacity: 0.6;
  font-size: 0.75rem;
`;
