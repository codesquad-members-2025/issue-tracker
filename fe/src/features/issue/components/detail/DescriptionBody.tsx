import styled from '@emotion/styled';
import ReactMarkdown from 'react-markdown';

const DEFAULT_DESCRIPTION = 'No description provided.';

interface DescriptionBodyProps {
  content: string | null;
}

export default function DescriptionBody({ content }: DescriptionBodyProps) {
  const rawContent = content ? content.replace(/\\n/g, '\n') : null;

  return (
    <StyledContent>
      <ReactMarkdown>{rawContent || DEFAULT_DESCRIPTION}</ReactMarkdown>
    </StyledContent>
  );
}

const StyledContent = styled.div`
  padding: 16px 24px 24px 24px;
  background-color: ${({ theme }) => theme.neutral.surface.strong};
  border-top: 1px solid ${({ theme }) => theme.neutral.border.default};
  ${({ theme }) => theme.typography.displayMedium16};
  color: ${({ theme }) => theme.neutral.text.default};

  /* 마크다운 랜더러용 css */
  line-height: 1.5;
  word-break: break-word;

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    font-weight: bold;
    margin: 16px 0 8px;
  }

  p {
    margin: 8px 0;
  }

  ul,
  ol {
    padding-left: 20px;
    margin: 8px 0;
  }

  li {
    list-style: disc;
  }

  a {
    color: ${({ theme }) => theme.neutral.text.default};
    text-decoration: underline;
  }
`;
