import { NavLink } from 'react-router-dom';
import styled from 'styled-components';
import { ghostButtonStyle } from '../components/Button';

// 공통 버튼 스타일
const StyledNavButton = styled(NavLink)`
  ${ghostButtonStyle}
  text-decoration: none;
  width: 160px;
  height: 40px;
  display: flex;
  gap: 4px;
  align-items: center;
  justify-content: center;

  &.active {
    font-weight: 700;
    color: ${({ theme }) => theme.text.strong};
    background-color: ${({ theme }) => theme.surface.bold};
  }
`;

// 좌우 버튼 스타일
const LeftButton = styled(StyledNavButton)``;

const RightButton = styled(StyledNavButton)`
  border-left: 1px solid ${({ theme }) => theme.border.default};
`;

export function LabelButton({ number }) {
  const textLabel = `레이블(${number})`;
  return (
    <LeftButton to="/labels">
      <svg
        width="17"
        height="16"
        viewBox="0 0 17 16"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <g clip-path="url(#clip0_30764_287)">
          <path
            d="M4.91683 4.66659H4.9235M13.9768 8.93992L9.19683 13.7199C9.073 13.8439 8.92595 13.9422 8.76408 14.0093C8.60222 14.0764 8.42872 14.111 8.2535 14.111C8.07828 14.111 7.90477 14.0764 7.74291 14.0093C7.58105 13.9422 7.43399 13.8439 7.31016 13.7199L1.5835 7.99992V1.33325H8.25016L13.9768 7.05992C14.2252 7.30974 14.3646 7.64767 14.3646 7.99992C14.3646 8.35217 14.2252 8.6901 13.9768 8.93992Z"
            stroke="#4E4B66"
            stroke-width="1.6"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </g>
        <defs>
          <clipPath id="clip0_30764_287">
            <rect width="16" height="16" fill="white" transform="translate(0.25)" />
          </clipPath>
        </defs>
      </svg>
      <span>{textLabel}</span>
    </LeftButton>
  );
}
export function MilestoneButton({ number }) {
  const textLabel = `마일스톤(${number})`;
  return (
    <RightButton to="/milestones">
      <svg
        width="17"
        height="16"
        viewBox="0 0 17 16"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          fill-rule="evenodd"
          clip-rule="evenodd"
          d="M8.5 0C8.69891 0 8.88968 0.0790176 9.03033 0.21967C9.17098 0.360322 9.25 0.551088 9.25 0.75V3H12.884C13.298 3 13.698 3.147 14.014 3.414L16.084 5.164C16.2782 5.32828 16.4342 5.53291 16.5412 5.76364C16.6482 5.99437 16.7037 6.24566 16.7037 6.5C16.7037 6.75434 16.6482 7.00563 16.5412 7.23636C16.4342 7.46709 16.2782 7.67172 16.084 7.836L14.014 9.586C13.6981 9.85325 13.2978 9.99993 12.884 10H9.25V15.25C9.25 15.4489 9.17098 15.6397 9.03033 15.7803C8.88968 15.921 8.69891 16 8.5 16C8.30109 16 8.11032 15.921 7.96967 15.7803C7.82902 15.6397 7.75 15.4489 7.75 15.25V10H3.5C3.03587 10 2.59075 9.81563 2.26256 9.48744C1.93437 9.15925 1.75 8.71413 1.75 8.25V4.75C1.75 3.784 2.534 3 3.5 3H7.75V0.75C7.75 0.551088 7.82902 0.360322 7.96967 0.21967C8.11032 0.0790176 8.30109 0 8.5 0ZM8.5 8.5H12.884C12.9431 8.49965 13.0001 8.47839 13.045 8.44L15.115 6.69C15.1424 6.66653 15.1645 6.63739 15.1796 6.60459C15.1947 6.57179 15.2025 6.53611 15.2025 6.5C15.2025 6.46389 15.1947 6.42821 15.1796 6.39541C15.1645 6.36261 15.1424 6.33347 15.115 6.31L13.045 4.56C13.0001 4.52161 12.9431 4.50035 12.884 4.5H3.5C3.4337 4.5 3.37011 4.52634 3.32322 4.57322C3.27634 4.62011 3.25 4.6837 3.25 4.75V8.25C3.25 8.388 3.362 8.5 3.5 8.5H8.5Z"
          fill="#4E4B66"
        />
      </svg>

      <span>{textLabel}</span>
    </RightButton>
  );
}
