/*
itemsArr의 구조
const itemsArr = [{ imgUrl: '~~~', label: 'XXX' isSelect: boolean }, ...];

*/

import styled from 'styled-components';
import { typography } from '@/styles/foundation';

const ScrollContainer = styled.div`
  height: 350px;
  width: 240px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
`;

const Title = styled.div`
  ${typography.selected.bold16};
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 8px 16px;
  border-bottom: 1px solid ${({ theme }) => theme.border.default};
  background-color: ${({ theme }) => theme.surface.default};
`;

const Content = styled.div`
  ${typography.available.medium16};
  display: flex;
  gap: 8px;
  align-items: center;
  padding: 10px 16px;
  background-color: ${({ theme, $isSelect }) =>
    $isSelect ? theme.surface.default : theme.surface.bold};
  &:hover {
    background-color: ${({ theme }) => theme.surface.bold};
  }
`;

const Separator = styled.hr`
  width: 80%;
  border: none;
  border-top: 1px solid ${({ theme }) => theme.border.default};
`;

export default function ScrollFilterList({ title, itemsArr }) {
  return (
    <ScrollContainer>
      <Title>{title}</Title>
      {itemsArr.map(({ label, imgUrl, isSelect }, idx, arr) => {
        return (
          <>
            <Content key={idx} $isSelect={isSelect}>
              <img src={imgUrl} alt={label} />
              <span>{label}</span>
            </Content>
            {idx !== arr.length - 1 && <Separator />}
          </>
        );
      })}
    </ScrollContainer>
  );
}
