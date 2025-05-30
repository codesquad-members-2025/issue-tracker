import { useDropzone } from 'react-dropzone';
import styled from 'styled-components';
import { typography } from '@/styles/foundation';
import useIssueDetailStore from '@/stores/IssueDetailStore';

const Zone = styled.div`
  ${typography.available.medium16}
  display:flex;
  flex-direction: column;
  gap: 4px;
  background-color: ${({ theme, $active }) => $active && `theme.surface.bold`};
`;

const Label = styled.div`
  display: flex;
  align-items: center;
  gap: 4px;
`;

const AcceptedFilesWrapper = styled.div`
  ${typography.display.medium12}
  display:flex;
  flex-direction: column;
`;

export default function FileDropzone({ onFiles, files }) {
  const {
    getRootProps,
    getInputProps,
    isDragActive,
    isDragReject,
    fileRejections, // 거절된 파일 목록
  } = useDropzone({
    onDrop: (accepted) => onFiles(accepted[0]), // ✅ Zustand 액션 주입
    accept: { 'image/*': [], 'application/pdf': [] },
    maxSize: 5 * 1024 * 1024, // 5 MB
    multiple: false,
  });

  return (
    <Zone {...getRootProps()} $active={isDragActive}>
      <input {...getInputProps()} />
      {isDragReject ? (
        '지원하지 않는 형식입니다.'
      ) : isDragActive ? (
        '여기에 놓으세요!'
      ) : (
        <Label>
          <svg
            width="16"
            height="16"
            viewBox="0 0 16 16"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <g clip-path="url(#clip0_31607_36338)">
              <path
                d="M14.2934 7.36678L8.1667 13.4934C7.41613 14.244 6.39815 14.6657 5.3367 14.6657C4.27524 14.6657 3.25726 14.244 2.5067 13.4934C1.75613 12.7429 1.33447 11.7249 1.33447 10.6634C1.33447 9.60199 1.75613 8.584 2.5067 7.83344L8.63336 1.70678C9.13374 1.2064 9.81239 0.925293 10.52 0.925293C11.2277 0.925293 11.9063 1.2064 12.4067 1.70678C12.9071 2.20715 13.1882 2.8858 13.1882 3.59344C13.1882 4.30108 12.9071 4.97973 12.4067 5.48011L6.27336 11.6068C6.02318 11.857 5.68385 11.9975 5.33003 11.9975C4.97621 11.9975 4.63688 11.857 4.3867 11.6068C4.13651 11.3566 3.99596 11.0173 3.99596 10.6634C3.99596 10.3096 4.13651 9.9703 4.3867 9.72011L10.0467 4.06678"
                stroke="#4E4B66"
                strokeWidth="1.6"
                strokeLinecap="round"
                strokeLinejoin="round"
              />
            </g>
            <defs>
              <clipPath id="clip0_31607_36338">
                <rect width="16" height="16" fill="white" />
              </clipPath>
            </defs>
          </svg>
          <span>파일 첨부하기</span>
        </Label>
      )}

      {files && (
        <AcceptedFilesWrapper>
          <span>{files.name}</span>
        </AcceptedFilesWrapper>
      )}
    </Zone>
  );
}
