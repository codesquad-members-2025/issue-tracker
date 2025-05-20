/** @jsxImportSource @emotion/react */
"use client";

import { useEffect, useState } from "react";
import styled from "@emotion/styled";
import Logo from "@/components/Logo";
import Profile from "@/components/Profile";
import Button from "@/components/Button";
import { FilterGroup, FilterDropdown } from "@components/FilterGroup";
import IssueListComponent from "@/components/IssueList";
import ThemeToggleBtn from "@/components/ThemeToggleBtn";
import type { Issue } from "@/types/issue";
import Link from "next/link";

const Page = styled.div`
  display: flex;
  flex-direction: column;
  height: 100vh;
  padding: 0 5rem;
  background-color: ${({ theme }) => theme.colors.surface.default};
`;

const TopBar = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: relative;
  height: 5.875rem;
  margin-bottom: 2rem;
`;

const Toolbar = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
`;

const LeftControls = styled.div`
  display: flex;
  align-items: center;
  width: 38.8vw;
  input {
    height: 2.5rem;
    padding: 0.5rem 1rem;
    font-size: 1rem;
    border: ${({ theme }) =>
      `${theme.border.default} ${theme.colors.border.default}`};
    border-radius: 0 ${({ theme }) => theme.radius.medium}
      ${({ theme }) => theme.radius.medium} 0;
    background-color: ${({ theme }) => theme.colors.surface.bold};
    flex: 1;
  }
`;

const RightControls = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5rem;
`;

const IssueFilterDropdown = styled(FilterDropdown)`
  width: 8rem;
  height: 2.5rem;
  border-radius: ${({ theme }) => theme.radius.medium} 0 0
    ${({ theme }) => theme.radius.medium};
`;

const LabelMoveBtn = styled(FilterDropdown)`
  width: 10rem;
  border-radius: ${({ theme }) => theme.radius.medium} 0 0
    ${({ theme }) => theme.radius.medium};
`;

const MileStoneMoveBtn = styled(FilterDropdown)`
  width: 10rem;
  border-radius: 0 ${({ theme }) => theme.radius.medium}
    ${({ theme }) => theme.radius.medium} 0;
`;

export default function IssuesPage() {
  const [issues, setIssues] = useState<Issue[]>([]);

  // fetch("/api/v1/issues?state=open")
  useEffect(() => {
    const fetchIssues = async () => {
      try {
        const res = await fetch("/mockDatas/issueMockData.json");
        if (!res.ok) {
          throw new Error(`HTTP error! status: ${res.status}`);
        }
        const json = await res.json();
        const data = json.data.issues;
        setIssues(data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchIssues();
  }, []);

  return (
    <Page>
      <TopBar>
        <Logo />
        <ThemeToggleBtn />
        <Profile />
      </TopBar>

      <Toolbar>
        <LeftControls>
          <IssueFilterDropdown
            label="필터"
            onClick={() => {
              /* 팝업 열기 */
            }}
            hasDownIcon={true}
          />
          <input type="text" placeholder="is:issue is:open" />
        </LeftControls>
        <RightControls>
          <FilterGroup>
            <Link href="/labels">
              <LabelMoveBtn
                label={`레이블(${3})`}
                hasDownIcon={false}
                onClick={() => {
                  /* 레이블 팝업 */
                }}
              />
            </Link>
            <Link href="/milestones">
              <MileStoneMoveBtn
                label={`마일스톤(${2})`}
                hasDownIcon={false}
                onClick={() => {
                  /* 마일스톤 팝업 */
                }}
              />
            </Link>
          </FilterGroup>
          <Link href="/issues/new">
            <Button
              onClick={() => {
                /* 이슈 작성 페이지로 이동 */
              }}
            >
              + 이슈 작성
            </Button>
          </Link>
        </RightControls>
      </Toolbar>

      <IssueListComponent issues={issues} />
    </Page>
  );
}
