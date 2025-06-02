import styled from '@emotion/styled';
import { type CommentAuthor } from '@/features/issue/types/issue';
import DescriptionHeader from '@/features/issue/components/detail/DescriptionHeader';
import DescriptionBody from '@/features/issue/components/detail/DescriptionBody';

export interface DescriptionBoxProps {
  content: string | null;
  author: CommentAuthor;
  createdAt: string;
}

export default function DescriptionBox({
  content,
  author,
  createdAt,
}: DescriptionBoxProps) {
  return (
    <Wrapper>
      <DescriptionHeader
        author={author}
        createdAt={createdAt}
        isAuthor={true} //TODO 로그인 기능 구현시 변경
      />
      <DescriptionBody content={content} />
    </Wrapper>
  );
}

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  border-radius: ${({ theme }) => theme.radius.large};
  border: 1px solid ${({ theme }) => theme.neutral.border.default};
  overflow: hidden;
`;
