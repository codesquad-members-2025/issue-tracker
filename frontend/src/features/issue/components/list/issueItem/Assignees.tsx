import styled from '@emotion/styled';
import Profile from '@/shared/components/Profile';

interface Props {
  assigneesProfileImages: string[];
}

const Assignees = ({ assigneesProfileImages }: Props) => {
  return (
    <AvatarStack>
      {assigneesProfileImages.slice(0, 5).map((url, idx) => (
        <Profile key={idx} size="sm" imageUrl={url} />
      ))}
      {assigneesProfileImages.length > 5 && (
        <ExtraText>+{assigneesProfileImages.length - 5}</ExtraText>
      )}
    </AvatarStack>
  );
};

export default Assignees;

const AvatarStack = styled.div`
  display: flex;
  align-items: center;
  position: relative;

  & > * {
    margin-left: -8px;
    position: relative;
  }

  & > *:first-of-type {
    margin-left: 0;
  }

  & > * {
    z-index: calc(10 - var(--order));
  }
`;

const ExtraText = styled.span`
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 0;

  color: ${({ theme }) => theme.neutral.text.strong};
  ${({ theme }) => theme.typography.displayMedium12};
`;
