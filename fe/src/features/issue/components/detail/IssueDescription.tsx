import styled from '@emotion/styled';
import { type CommentAuthor } from '../../types/issue';
import DescriptionHeader from './DescriptionHeader';
import DescriptionBody from './DescriptionBody';

interface IssueDescriptionProps {
  content: string;
  author: CommentAuthor;
  createdAt: string;
}

export default function IssueDescription({
  content,
  author,
  createdAt,
}: IssueDescriptionProps) {
  return (
    <Wrapper>
      <DescriptionHeader
        profileImageUrl={author.profileImage}
        nickname={author.nickname}
        createdAt={createdAt}
        isAuthor={true}
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
