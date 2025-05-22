// useFilterModalStore의 filterEntry에서 데이터를 가져와서 렌더링한다.

import styled from 'styled-components';
import { useState } from 'react';
import React from 'react';
import { typography } from '@/styles/foundation';
import useIssueDetailStore from '@/stores/IssueDetailStore';
import useDataFetch from '@/hooks/useDataFetch';
import { getToggleButton } from './getToggleButton';
import getOptionWithToken from '@/utils/getOptionWithToken/getOptionWithToken';
import AuthorInform from '../AuthorInform';

const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5); /* 배경 어두움 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
`;

const ToggleContainer = styled.div`
  display: flex;
  width: 224px;
  flex-direction: column;
  position: relative;
`;

const MenuTriggerButton = styled.button`
  ${typography.available.medium16};
  color: ${({ theme }) => theme.text.default};
  display: flex;
  width: 100%;
  justify-content: space-between;
  align-items: center;
  cursor: pointer;

  &::after {
    content: '';
    transition: transform 0.3s;
    border-right: 2px solid currentColor;
    border-bottom: 2px solid currentColor;
    width: 8px;
    height: 8px;
    transform: rotate(-45deg);
    margin-left: auto;
  }

  ${(props) =>
    props.open &&
    css`
      &::after {
        transform: rotate(45deg);
      }
    `}
`;

const SubMenuContainer = styled.div`
  display: ${(props) => (props.open ? 'block' : 'none')};
`;

const SubMenuWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;
const SubMenuButton = styled.button`
  ${typography.available.medium16};
  color: ${({ theme, $isSelect }) => ($isSelect ? theme.text.strong : theme.text.default)};
  display: flex;
  align-items: center;
  height: 50px;
  display: flex;
  gap: 8px;

  &:hover {
    color: ${({ theme }) => theme.text.strong};
  }
  img {
    width: 20px;
    height: 20px;
  }
`;

export default function AddStatusToggle({ toggleType, triggerButtonLabel, itemsArr, onClick }) {
  const [open, setOpen] = useState(false);
  const { toggleAssignee, toggleLabel, toggleMilestone } = useIssueDetailStore((state) => ({
    toggleAssignee: state.toggleAssignee,
    toggleLabel: state.toggleLabel,
    toggleMilestone: state.toggleMilestone,
  }));

  const fetchData = useDataFetch();

  function handleToggleOption(type, id) {
    switch (type) {
      case 'label':
        toggleLabel(id);
      case 'milestone':
        toggleMilestone(id);
      default:
        toggleAssignee(id);
    }
  }

  function handleCloseOverlay(context) {
    setOpen((prev) => !prev);
    if (context === 'detail') {
    }
  }
  function handleToggle() {
    setOpen((prev) => !prev);
  }

  return (
    <ToggleContainer>
      <MenuTriggerButton onClick={handleToggle}>{triggerButtonLabel}</MenuTriggerButton>
      <SubMenuContainer open={open}>
        <SubMenuWrapper>
          {itemsArr.map((item) => {
            return (
              <React.Fragment key={item.id}>
                {getToggleButton(toggleType, item, onClick, selected)}
              </React.Fragment>
            );
          })}
        </SubMenuWrapper>
      </SubMenuContainer>
      <Overlay />
    </ToggleContainer>
  );
}
