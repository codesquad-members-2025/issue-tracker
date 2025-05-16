import styled from 'styled-components';
import FilterBar from './FilterBar';
import { NavigateTabs } from './NavigateTabs';

const Container = styled.div`
  display: flex;
  padding: 32px 0px 24px 0px;
  align-items: center;
  justify-content: space-between;
`;

export default function MainPageHeaderTap() {
  return (
    <Container>
      <FilterBar />
      <NavigateTabs />
    </Container>
  );
}
