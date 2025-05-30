import styled from 'styled-components';
import isImageFile from '@/utils/common/isImageFile';
import { typography } from '@/styles/foundation';

const CommentWrapper = styled.div`
  ${typography.display.medium16}
  background-color:${({ theme }) => theme.surface.strong};
  white-space: pre-line;
  padding: 16px 24px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: flex-start;
`;

export default function CommentDisplayArea({ content, issueFileUrl }) {
  return (
    <CommentWrapper>
      <span>{content}</span>
      {isImageFile ? <img src={issueFileUrl} /> : <a href={issueFileUrl}>첨부파일입니다.</a>}
    </CommentWrapper>
  );
}
