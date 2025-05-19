import styled from 'styled-components';
import { typography } from '@/styles/foundation';
import { LargeContainerButton } from '../components/ContainerButtons';
import useFilterStore from '@/stores/filterStore';
import { useApplyQueryParams } from '@/utils/queryParams/useApplyQueryParams';
import useFilterModalStore from '@/stores/detailFilterModalStore';

const StyledButton = styled(LargeContainerButton)`
  display: flex;
  gap: 8px;
  align-items: center;
`;

export default function SearchButton() {
  const selectedFilters = useFilterStore((state) => state.selectedFilters);
  const resetFilters = useFilterStore((state) => state.resetFilters);

  const closeModal = useFilterModalStore((state) => state.closeModal);
  const applyQueryParams = useApplyQueryParams();

  function searchHandler() {
    closeModal();
    applyQueryParams(selectedFilters);
    resetFilters();
  }
  const buttonLabel = '검색';
  return <StyledButton onClick={() => searchHandler()}>{buttonLabel}</StyledButton>;
}
