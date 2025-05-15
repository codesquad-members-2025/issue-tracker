/*
설계 방향: 상위에서 prop을 전달 받는게 아니라. 쿼리파람 추출, 조작해서 하위의 컴포넌트에게 prop을 전달한다.
항상 메인페이지 ("/")의 url 상태일 경우에는 디폴트 값으로 isOpen=true인 경우와 똑같이 처리한다.
나머지 필터 조작도 prop을 전달하는게 아니라 쿼리파람을 조작함으로 상태를 변경한다.
*/

import styled from 'styled-components';
import { FilterSearchField } from '@/base-ui/issueListPage/mainPageHeaderTap/FilteredTab';
import { DropdownMenuTemplate } from '@/utils/dropDown/DropdownMenuTemplate';

const Container = styled.div`
  border: 1px solid ${({ theme }) => theme.border.default};
  border-radius: 12px;
  display: flex;
  width: 560px;
  background-color: ${({ theme, $isActive }) =>
    $isActive ? theme.surface.strong : theme.surface.bold};
`;

export default function FilterBar() {
  const isActive = selectedFilters.length > 0;

  function menuItems(selectedFilters) {}

  return (
    <Container $isActive={isActive}>
      <DropdownMenuTemplate triggerLabel="필터" menuWidth="240px" label={'이슈 필터'} />
      <FilterSearchField />
    </Container>
  );
}
