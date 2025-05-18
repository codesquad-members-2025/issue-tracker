import styled from 'styled-components';
import { ScrollFilterList, SearchButton } from '@/base-ui/detailFilterModal';
import { typography } from '@/styles/foundation';
import useFilterModalStore from '@/stores/detailFilterModalStore';
import useFilterStore from '@/stores/filterStore';

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

const Container = styled.div`
  background-color: ${({ theme }) => theme.surface.default};
  position: relative;
  color: ${({ theme }) => theme.text.default};
  height: 100px;
  border-radius: 8px;
  z-index: 1000;
`;

const Header = styled.div`
  ${typography.display.bold32};
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Body = styled.div`
  display: flex;
`;

const Footer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 120px;
  padding: 30px 0px;
`;

const CloseIcon = styled.svg`
  fill: ${({ theme }) => theme.text.default};

  &:hover {
    fill: ${({ theme }) => theme.danger.border};
  }
`;

const CloseButton = styled.button`
  position: relative;
  top: 8px;
  right: 8px;
`;

const SearchIcon = styled.svg`
  fill: ${({ theme }) => theme.surface.default};
`;

const Title = styled.div`
  position: absolute;
  top: 24px;
  left: 10px;
`;

// 외부에서 prop을 받는게 아니라 전역의 store를 끌고 와서 상태를 변경한다.
export default function DetailFilterModal() {
  const { filterEntry, closeModal } = useFilterModalStore((state) => ({
    filterEntry: state.filterEntry,
    closeModal: state.closeModal,
  }));
  const resetFilters = useFilterStore((state) => state.resetFilters); //검색 버튼의 콜백함수에 사용
  const titleLabel = '상세필터';
  const searchButton = '필터 적용';

  function closeModalHandler() {
    resetFilters();
    closeModal();
  }
  return (
    <Overlay onClick={closeModalHandler}>
      <Container>
        <Header>
          <Title>{titleLabel}</Title>
          <CloseButton onClick={closeModalHandler}>
            <CloseIcon
              xmlns="http://www.w3.org/2000/svg"
              height="24px"
              viewBox="0 -960 960 960"
              width="24px"
              fill="currentColor"
            >
              <path d="m256-200-56-56 224-224-224-224 56-56 224 224 224-224 56 56-224 224 224 224-56 56-224-224-224 224Z" />
            </CloseIcon>
          </CloseButton>
        </Header>
        <Body>
          {Object.entries(filterEntry).map((entryArr) => {
            return (
              //row방향 필터 4개 스크롤 렌더 로직
              <React.Fragment key={entryArr[0]}>
                <ScrollFilterList title={entryArr[0]} itemsArr={entryArr[1]} />;
              </React.Fragment>
            );
          })}
        </Body>
        <Footer>
          <SearchButton>
            <SearchIcon
              xmlns="http://www.w3.org/2000/svg"
              height="24px"
              viewBox="0 -960 960 960"
              width="24px"
              fill="currentColor"
            >
              <path d="M765-144 526-383q-30 22-65.79 34.5-35.79 12.5-76.18 12.5Q284-336 214-406t-70-170q0-100 70-170t170-70q100 0 170 70t70 170.03q0 40.39-12.5 76.18Q599-464 577-434l239 239-51 51ZM384-408q70 0 119-49t49-119q0-70-49-119t-119-49q-70 0-119 49t-49 119q0 70 49 119t119 49Z" />
            </SearchIcon>
            <span>{searchButton}</span>
          </SearchButton>
        </Footer>
      </Container>
    </Overlay>
  );
}
