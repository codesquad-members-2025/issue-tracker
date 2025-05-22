//

/*
# 사용가이드

## 입력
- toggleType -> 어떤 토글 타입 버튼인지 문자열로 입력한다.(ex. 'label' or 'milestone')
- triggerButtonLabel -> 토글의 트리거 버튼에 어떤 문구를 보여줄 건지 입력한다. (ex. "레이블", "마일스톤")_ 주로 유저에게 보여줄 문구를 작성한다.
- itemsArr -> useFilterModalStore의 filterEntry에서 데이터를 입력한다. (ex. 라벨의 객체 배열, 마일스톤의 객체 배열...)
- pageTypeContext -> 어떤 페이지 타입에서 사용되는지 입력한다. (ex. 'detail' 문자열로 입력


## 로직
해당 컴포넌트에서 다음의 로직이 알아서 실행됩니다.
- 이슈 디테일 스토어 업데이트
- 서버에 PATCH 요청

*/

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
  background-color: rgba(0, 0, 0, 0.3); /* 배경 어두움 */
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
  z-index: 1000;

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
  z-index: 1000;
`;

const SubMenuWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export default function AddStatusToggle({
  toggleType,
  triggerButtonLabel,
  itemsArr,
  pageTypeContext = null,
}) {
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
      /*
      BE 에서 API 나와야 적용 가능
      - PATCH 할때 구조 협의 후 구현 ***TODO***      
      - fetchData()
      */
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
                {getToggleButton(toggleType, item, handleToggleOption, selected)}
              </React.Fragment>
            );
          })}
        </SubMenuWrapper>
      </SubMenuContainer>
      <Overlay onClick={() => handleCloseOverlay(pageTypeContext)} />
    </ToggleContainer>
  );
}
