/** @jsxImportSource @emotion/react */
"use client";

import { useEffect, useState } from "react";
import styled from "@emotion/styled";
import Logo from "@/components/Logo";
import Profile from "@/components/Profile";
import Button from "@/components/Button";
import { FilterGroup, FilterDropdown } from "@/components/FilterGroup";
import IssueItem from "@/components/IssueItem";
import ThemeToggleBtn from "@/components/ThemeToggleBtn";
import type { Issue } from "@/types/issue";

const Page = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.5rem; /* 24px */
  padding: 2rem; /* 32px */
  background-color: ${({ theme }) => theme.colors.surface.default};
`;

const TopBar = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Toolbar = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const LeftControls = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5rem;
  input {
    padding: 0.5rem 1rem;
    font-size: 1rem;
    border: ${({ theme }) =>
      `${theme.border.default} ${theme.colors.border.default}`};
    border-radius: ${({ theme }) => theme.radius.medium};
    background-color: ${({ theme }) => theme.colors.surface.strong};
    flex: 1;
  }
`;

const RightControls = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5rem;
`;

const IssueList = styled.div`
  display: flex;
  flex-direction: column;
  border-top: ${({ theme }) =>
    `${theme.border.default} ${theme.colors.border.default}`};
`;

export default function IssuesPage() {
  const [issues, setIssues] = useState<Issue[]>([]);

  useEffect(() => {
    fetch("/api/v1/issues?state=open")
      .then((res) => res.json())
      .then((json) => {
        const data = json.data.issues;
        const mapped: Issue[] = data.map((i: any) => ({
          id: i.id,
          number: i.id,
          title: i.title,
          createdBy: i.writer.username,
          createdAt: i.createdAt,
          avatarUrl: i.writer.profileImageUrl,
          labels: i.labels.map((l: any) => l.name),
          milestone: i.milestone?.title,
          state: i.isOpen ? "open" : "closed",
        }));
        setIssues(mapped);
      });
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
          <FilterDropdown
            label="필터"
            onClick={() => {
              /* 팝업 열기 */
            }}
          />
          <input type="text" placeholder="is:issue is:open" />
        </LeftControls>
        <RightControls>
          <FilterGroup>
            <FilterDropdown
              label="레이블(3)"
              onClick={() => {
                /* 레이블 팝업 */
              }}
            />
            <FilterDropdown
              label="마일스톤(2)"
              onClick={() => {
                /* 마일스톤 팝업 */
              }}
            />
          </FilterGroup>
          <Button
            onClick={() => {
              /* 이슈 작성 페이지로 이동 */
            }}
          >
            + 이슈 작성
          </Button>
        </RightControls>
      </Toolbar>

      <IssueList>
        {issues.map((issue) => (
          <IssueItem key={issue.id} issue={issue} />
        ))}
      </IssueList>
    </Page>
  );
}
