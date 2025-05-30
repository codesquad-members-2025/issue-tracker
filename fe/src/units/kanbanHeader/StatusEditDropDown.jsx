import { DropdownMenuTemplate } from '@/utils/dropDown/DropdownMenuTemplate';
import useCheckBoxStore from '@/stores/useCheckBoxStore';
import useDataFetch from '@/hooks/useDataFetch';
import { TOGGLE_STATUS_URL } from '@/api/toggleStatus';
import { useEffect, useRef } from 'react';
import { useLocation } from 'react-router-dom';
import getOptionWithToken from '@/utils/getOptionWithToken/getOptionWithToken';
import { useAuthStore } from '@/stores/authStore';

function getSelectedId(checkBoxEntry) {
  const selectedIdArr = [];
  for (const id in checkBoxEntry) {
    if (checkBoxEntry[id]) {
      selectedIdArr.push(Number(id));
    }
  }
  return selectedIdArr;
}

export default function StatusEditDropDown({ onPatchSuccess }) {
  const accessToken = useAuthStore((state) => state.accessToken);
  const checkBoxEntry = useCheckBoxStore((state) => state.checkBoxEntry);
  const fetchType = '이슈 상태 조작';
  const { response, fetchData } = useDataFetch({ fetchType });
  const triggerButtonLabel = '상태수정';
  const dropDownLabel = '상태 변경';
  const selectedIdArr = getSelectedId(checkBoxEntry);
  const patchRef = useRef(false);
  const location = useLocation();

  function getOptionObject(isOpen) {
    const optionObject = {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ id: selectedIdArr, isOpen: isOpen }),
    };
    return optionObject;
  }
  const items = [
    {
      label: '선택한 이슈 열기',
      isSelected: true,
      onClick: () => {
        fetchData(TOGGLE_STATUS_URL, getOptionWithToken(getOptionObject(true), accessToken));
        patchRef.current = true;
      },
    },
    {
      label: '선택한 이슈 닫기',
      isSelected: true,
      onClick: () => {
        fetchData(TOGGLE_STATUS_URL, getOptionWithToken(getOptionObject(false), accessToken));
        patchRef.current = true;
      },
    },
  ];

  useEffect(() => {
    if (!response?.success) return;
    if (!patchRef.current) return;
    onPatchSuccess();
    patchRef.current = false;
  }, [response]);

  return (
    <DropdownMenuTemplate
      triggerLabel={triggerButtonLabel}
      menuWidth="240px"
      label={dropDownLabel}
      items={items}
    />
  );
}
