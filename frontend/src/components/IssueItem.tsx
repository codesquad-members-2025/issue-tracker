/** @jsxImportSource @emotion/react */
import styled from "@emotion/styled";
import type { Issue } from "@/types/issue";

// 체크박스 + 아이콘 + 제목+레이블+마일스톤 + 메타 정보
const Item = styled.div`
  display: grid;
  grid-template-columns: auto 1.5rem 1fr auto;
  align-items: center;
  gap: 0.75rem; /* 12px */
  padding: 1rem; /* 16px */
  border-bottom: ${({ theme }) =>
    `${theme.border.default} ${theme.colors.border.default}`};
`;

const Title = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 1rem;
  font-weight: 500;
`;

const LabelBadge = styled.span`
  padding: 0.2rem 0.4rem; /* 4px 8px */
  font-size: 0.75rem; /* 12px */
  border-radius: ${({ theme }) => theme.radius.half};
  background-color: ${({ theme }) => theme.colors.accent.blue};
  color: white;
`;

const MilestoneBadge = styled.span`
  padding: 0.2rem 0.4rem;
  font-size: 0.75rem;
  border-radius: ${({ theme }) => theme.radius.medium};
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colors.accent.navy}`};
  color: ${({ theme }) => theme.colors.accent.navy};
`;

const Meta = styled.div`
  font-size: 0.875rem; /* 14px */
  color: ${({ theme }) => theme.colors.neutralText.weak};
  white-space: nowrap;
`;

export const IssueItem: React.FC<{ issue: Issue }> = ({ issue }) => (
  <Item>
    <input type="checkbox" />
    {/* open/closed 아이콘은 예시 */}
    {issue.state === "open" ? <span>🔓</span> : <span>🔒</span>}
    <Title>
      {issue.title}
      {issue.labels?.map((l) => (
        <LabelBadge key={l}>{l}</LabelBadge>
      ))}
      {issue.milestone && <MilestoneBadge>{issue.milestone}</MilestoneBadge>}
    </Title>
    <Meta>
      #{issue.number} · {issue.createdBy} ·{" "}
      {new Date(issue.createdAt).toLocaleString()}
    </Meta>
  </Item>
);

export default IssueItem;
