import { DropdownMenuTemplate } from '@/utils/dropDown/DropdownMenuTemplate';
import useCheckBoxStore from '@/stores/useCheckBoxStore';
import useDataFetch from '@/hooks/useDataFetch';

function getSelectedId(checkBoxEntry) {
  const selectedIdArr = [];
  for (const id in checkBoxEntry) {
    if (checkBoxEntry[id]) {
      selectedIdArr.push(Number(id));
    }
  }
  return selectedIdArr;
}

export default function StatusEditDropDown() {
  const checkBoxEntry = useCheckBoxStore((state) => state.checkBoxEntry);
  const fetchType = '이슈 상태 조작';
  const { fetchData } = useDataFetch({ fetchType });
  const triggerButtonLabel = '상태수정';
  const dropDownLabel = '상태 변경';
  const selectedIdArr = getSelectedId(checkBoxEntry);
  const istems = [
    {
      label: '선택한 이슈 열기',
      isSelected: true,
      onClick: () => handleClick('홍길동'),
    },
    {
      label: '선택한 이슈 닫기',
      isSelected: true,
      onClick: () => handleClick('홍길동'),
    },
  ];
  return (
    <DropdownMenuTemplate
      triggerLabel={triggerButtonLabel}
      menuWidth="240px"
      label={dropDownLabel}
    />
  );
}
