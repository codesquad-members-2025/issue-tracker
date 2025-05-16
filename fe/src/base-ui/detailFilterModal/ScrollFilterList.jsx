/*
itemsArr의 구조
const itemsArr = [{ imgUrl: '~~~', label: 'XXX' }, ...];

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
`;

const Separator = styled.hr`
  margin: 0.5rem 0;
  border: none;
  border-top: 1px solid ${({ theme }) => theme.border.default};
`;

export function ScrollFilterList({ title, itemsArr }) {
  return (
    <ScrollContainer>
      <Title>{title}</Title>
      {itemsArr.map((item, idx, arr) => {
        return (
          <>
            <Content key={idx}>
              <img src={item.imgUrl} alt={item.label} />
              <span>{item.label}</span>
            </Content>
            {idx !== arr.length - 1 && <Separator />}
          </>
        );
      })}
    </ScrollContainer>
  );
}
