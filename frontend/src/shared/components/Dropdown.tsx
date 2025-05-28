import { useState } from 'react';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import DropdownIndicator from './DropdownIndicator';

interface DropdownProps {
  label: string;
  children: React.ReactNode;
}

export default function Dropdown({ label, children }: DropdownProps) {
  const [open, setOpen] = useState(false);

  return (
    <Wrapper>
      <Trigger onClick={() => setOpen(prev => !prev)}>
        <DropdownIndicator label={label} />
      </Trigger>
      {open && (
        <Panel>
          <PanelTitle>{label}</PanelTitle>
          <PanelContent>{children}</PanelContent>
        </Panel>
      )}
    </Wrapper>
  );
}

const Wrapper = styled.div`
  position: relative;
  display: inline-block;
`;

const Trigger = styled.button`
  all: unset;
  cursor: pointer;
  display: flex;
  align-items: center;
`;

const Panel = styled.div`
  ${({ theme }) => css`
    width: 240px;
    position: absolute;
    top: 100%;
    right: 0;
    z-index: 1000;
    border: 1px solid ${theme.neutral.border.default};
    border-radius: 16px;
    background-color: ${theme.neutral.surface.strong};
    overflow: hidden;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  `}
`;

const PanelTitle = styled.div`
  ${({ theme }) => css`
    height: 32px;
    padding: 8px 16px;
    display: flex;
    align-items: center;
    color: ${theme.neutral.text.weak};
    background-color: ${theme.neutral.surface.default};
    border-bottom: 1px solid ${theme.neutral.border.default};

    ${theme.typography.displayMedium12}
  `}
`;

const PanelContent = styled.ul`
  display: flex;
  flex-direction: column;
`;
