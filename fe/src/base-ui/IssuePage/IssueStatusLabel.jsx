import styled from 'styled-components';
import { typography } from '@/styles/foundation';

const Tag = styled.div`
  ${typography.display.medium12}
  display:flex;
  gap: 4px;
  align-items: center;
  padding: 8px 16px;
  border-radius: 16px;
  color: ${({ theme }) => theme.brand.text.default};

  background-color: ${({ theme, $isOpen }) =>
    $isOpen ? theme.palette.blue : 'rgb(171, 125, 248)'};
`;

export default function IssueStatusLabel({ isOpen }) {
  const label = isOpen ? '열린 이슈' : '닫힌 이슈';
  return (
    <Tag $isOpen={isOpen}>
      {isOpen ? (
        <svg
          width="16"
          height="16"
          viewBox="0 0 16 16"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <g clip-path="url(#clip0_31607_36703)">
            <path
              d="M8 14.6668C11.6819 14.6668 14.6667 11.6821 14.6667 8.00016C14.6667 4.31826 11.6819 1.3335 8 1.3335C4.3181 1.3335 1.33334 4.31826 1.33334 8.00016C1.33334 11.6821 4.3181 14.6668 8 14.6668Z"
              stroke="currentColor"
              strokeWidth="1.6"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
            <path
              d="M8 5.3335V8.00016"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
            <path
              d="M8 10.6665H8.00667"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
          </g>
          <defs>
            <clipPath id="clip0_31607_36703">
              <rect width="16" height="16" fill="white" />
            </clipPath>
          </defs>
        </svg>
      ) : (
        <svg
          width="16"
          height="16"
          viewBox="0 0 16 16"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <g clip-path="url(#clip0_14852_5722)">
            <path
              d="M14 5.33337V14H2V5.33337"
              stroke="currentColor"
              strokeWidth="1.6"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
            <path
              d="M15.3332 2H0.666504V5.33333H15.3332V2Z"
              stroke="currentColor"
              strokeWidth="1.6"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
            <path
              d="M6.6665 8H9.33317"
              stroke="currentColor"
              strokeWidth="1.6"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
          </g>
          <defs>
            <clipPath id="clip0_14852_5722">
              <rect width="16" height="16" fill="white" />
            </clipPath>
          </defs>
        </svg>
      )}
      <span>{label}</span>
    </Tag>
  );
}
