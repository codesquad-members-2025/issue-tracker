import styled from 'styled-components';
import { SmallOutlineButton } from '../components/OutlineButtons';
import { SmallContainerButton } from '../components/ContainerButtons';

const OutlineBtn = styled(SmallOutlineButton)`
  gap: 4px;
`;

const ContainerBtn = styled(SmallContainerButton)`
  gap: 4px;
`;

function EditTitleBtn({ onClick }) {
  return (
    <OutlineBtn onClick={onClick}>
      <svg
        width="17"
        height="16"
        viewBox="0 0 17 16"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M13.833 9.77317V13.3332C13.833 13.6868 13.6925 14.0259 13.4425 14.276C13.1924 14.526 12.8533 14.6665 12.4997 14.6665H3.16634C2.81272 14.6665 2.47358 14.526 2.22353 14.276C1.97348 14.0259 1.83301 13.6868 1.83301 13.3332V3.99984C1.83301 3.64622 1.97348 3.30708 2.22353 3.05703C2.47358 2.80698 2.81272 2.6665 3.16634 2.6665H6.72634"
          stroke="currentColor"
          strokeWidth="1.6"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
        <path
          d="M12.4997 1.3335L15.1663 4.00016L8.49967 10.6668H5.83301V8.00016L12.4997 1.3335Z"
          stroke="currentColor"
          strokeWidth="1.6"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
      </svg>
      <span>제목 편집</span>
    </OutlineBtn>
  );
}
function CloseIssueBtn({ onClick }) {
  return (
    <OutlineBtn onClick={onClick}>
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
      <span>이슈 닫기</span>
    </OutlineBtn>
  );
}
function OpenIssueBtn({ onClick }) {
  return (
    <OutlineBtn onClick={onClick}>
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
      <span>이슈 열기</span>
    </OutlineBtn>
  );
}
function CancelEditBtn({ onClick }) {
  return (
    <OutlineBtn onClick={onClick}>
      <svg
        width="16"
        height="16"
        viewBox="0 0 16 16"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M11.2999 4.70026L4.70025 11.2999M4.7002 4.7002L11.2999 11.2999"
          stroke="currentColor"
          stroke-width="1.6"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
      </svg>

      <span>편집 취소</span>
    </OutlineBtn>
  );
}

function CompleteEditBtn({ onClick, isValid }) {
  return (
    <ContainerBtn disabled={!isValid} onClick={onClick}>
      <svg
        width="17"
        height="16"
        viewBox="0 0 17 16"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M13.833 9.77317V13.3332C13.833 13.6868 13.6925 14.0259 13.4425 14.276C13.1924 14.526 12.8533 14.6665 12.4997 14.6665H3.16634C2.81272 14.6665 2.47358 14.526 2.22353 14.276C1.97348 14.0259 1.83301 13.6868 1.83301 13.3332V3.99984C1.83301 3.64622 1.97348 3.30708 2.22353 3.05703C2.47358 2.80698 2.81272 2.6665 3.16634 2.6665H6.72634"
          stroke="currentColor"
          strokeWidth="1.6"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
        <path
          d="M12.4997 1.3335L15.1663 4.00016L8.49967 10.6668H5.83301V8.00016L12.4997 1.3335Z"
          stroke="currentColor"
          strokeWidth="1.6"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
      </svg>

      <span>편집 완료</span>
    </ContainerBtn>
  );
}

export { EditTitleBtn, CancelEditBtn, CloseIssueBtn, CompleteEditBtn, OpenIssueBtn };
