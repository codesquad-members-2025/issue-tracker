/** @jsxImportSource @emotion/react */
"use client";

import styled from "@emotion/styled";
import IssueTabFilter from "./IssueTabFilter";
import { FilterGroup, FilterDropdown } from "@components/FilterGroup";
// import ChevronDownIcon from "@/assets/icons/chevronDown.svg?react";

const HeaderWrapper = styled.div`
  display: flex;
  align-items: center;
  padding: 16px 32px;

  /* border-bottom: 1px solid ${({ theme }) => theme.colors.border.default}; */
  background-color: ${({ theme }) => theme.colors.surface.default};
  color: ${({ theme }) => theme.colors.neutralText.default};
`;

const LeftSection = styled.div`
  display: flex;
  justify-content: start;
  align-items: center;
  flex: 1;
  gap: 2rem;
`;

const RightSection = styled.div`
  display: flex;
  gap: 1rem;
`;

const Checkbox = styled.input`
  /* display: flex;
  justify-content: start;
  align-items: start; */
  width: 1rem;
  height: 1rem;
  cursor: pointer;
`;

const IndicatorDropdown = styled(FilterDropdown)`
  display: inline-flex;
  align-items: center;
  justify-content: space-between;
  gap: 4px;
  min-width: 80px;
  background-color: transparent;
  border: none;

  cursor: pointer;
`;

interface ListHeaderProps {
  openCount: number;
  closeCount: number;
  selected: "open" | "closed";
  onChangeTab: (status: "open" | "closed") => void;
}

export default function IssueListHeader({
  openCount,
  closeCount,
  selected,
  onChangeTab,
}: ListHeaderProps) {
  return (
    <HeaderWrapper>
      <LeftSection>
        <Checkbox type="checkbox" />
        {/* TODO 공통 체크박스로 변경 */}

        <IssueTabFilter
          openCount={openCount}
          closeCount={closeCount}
          selected={selected}
          onChangeTab={onChangeTab}
        />
      </LeftSection>

      <RightSection>
        <IndicatorDropdown
          label="담당자"
          hasDownIcon={true}
          onClick={() => {}}
        />
        <IndicatorDropdown
          label="레이블"
          hasDownIcon={true}
          onClick={() => {}}
        />
        <IndicatorDropdown
          label="마일스톤"
          hasDownIcon={true}
          onClick={() => {}}
        />
        <IndicatorDropdown
          label="작성자"
          hasDownIcon={true}
          onClick={() => {}}
        />
      </RightSection>
    </HeaderWrapper>
  );
}
