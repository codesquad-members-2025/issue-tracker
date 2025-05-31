import styled from 'styled-components';
import useQueryParams from '@/utils/queryParams/useQueryParams';
import { NextButton, PrevButton } from '@/base-ui/pagination/swipeButton';
import { useSearchParams } from 'react-router-dom';
import { typography } from '@/styles/foundation';

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 30px;
`;

const PageLabel = styled.div`
  ${typography.display.medium16}
  color:${({ theme }) => theme.text.default};
`;

export default function DefaultSwipeButton() {
  const [searchParams] = useSearchParams();
  const currentPage = searchParams.get('page');
  const editParams = useQueryParams();

  function NextSwipeHandler() {
    editParams({ page: Number(currentPage) + 1 });
  }
  function PrevSwipeHandler() {
    editParams({ page: Number(currentPage) - 1 });
  }

  return (
    <Wrapper>
      <PrevButton onClick={PrevSwipeHandler} />
      <PageLabel>{currentPage}</PageLabel>
      <NextButton onClick={NextSwipeHandler} />
    </Wrapper>
  );
}
