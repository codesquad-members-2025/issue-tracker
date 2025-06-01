import styled from '@emotion/styled';
import { type Comment, type CommentAuthor } from '@/features/issue/types/issue';
import IssueDescription from '@/features/issue/components/detail/IssueDescription';
import CommentList from '@/features/issue/components/detail/CommentList';
import CommentEditor from '@/features/issue/components/detail//CommentEditor';

interface IssueMainSectionProps {
  comments: Comment[];
  content: string | null;
  createdAt: string;
  author: CommentAuthor;
}

export default function IssueMainSection({
  comments,
  content,
  createdAt,
  author,
}: IssueMainSectionProps) {
  return (
    <MainWrapper>
      <IssueDescription
        content={content}
        createdAt={createdAt}
        author={author}
      />
      {comments.length > 0 && <CommentList comments={comments} />}
      <CommentEditor />
    </MainWrapper>
  );
}

const MainWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
  flex: 1;
`;
