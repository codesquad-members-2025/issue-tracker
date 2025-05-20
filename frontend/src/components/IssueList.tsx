/** @jsxImportSource @emotion/react */
"use client";

import styled from "@emotion/styled";
import IssueItem from "@/components/IssueItem";
import IssueListHeader from "@components/IssueListHeader";
import type { Issue } from "@/types/issue";
import Link from "next/link";

const Container = styled.div`
  overflow: hidden;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colors.border.default}`};
  border-radius: ${({ theme }) => theme.radius.medium};
`;

const ListContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

interface IssueListProps {
  issues: Issue[];
}

const IssueList: React.FC<IssueListProps> = ({ issues }) => (
  <Container>
    <IssueListHeader
      openCount={0}
      closeCount={0}
      selected="open"
      onChangeTab={() => {}}
    />
    <ListContainer>
      {issues.map((issue) => (
        <Link
          key={issue.id}
          href={`/issues/${issue.id}`}
          // 앱 라우터라면 스타일드 컴포넌트 같은 wrapper 안 써도 됩니다.
        >
          <IssueItem key={issue.id} issue={issue} />
        </Link>
      ))}
    </ListContainer>
  </Container>
);

// const IssueList: React.FC<IssueListProps> = () => {
//   return (
//     <ListContainer>
//       <IssueListHeader />
//       <IssueItem key={1} />
//     </ListContainer>
//   );
// };

export default IssueList;
