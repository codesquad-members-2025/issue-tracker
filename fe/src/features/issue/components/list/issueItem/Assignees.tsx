import styled from '@emotion/styled';

interface Props {
  assigneesProfileImages: string[];
}

const Assignees = ({ assigneesProfileImages }: Props) => {
  return (
    <AvatarStack>
      {assigneesProfileImages.slice(0, 5).map((url, idx) => (
        <AssigneeAvatar
          key={idx}
          src={url}
          alt={`assignee-${idx}`}
          style={{ zIndex: 5 - idx }}
        />
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
`;

const AssigneeAvatar = styled.img`
  width: 20px;
  height: 20px;
  border-radius: 50%;
  border: 1px solid white;
  position: relative;
  margin-left: -8px;

  &:first-of-type {
    margin-left: 0;
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

  color: ${({ theme }) => theme.textColor.strong};
  ${({ theme }) => theme.typography.displayMedium12};
`;
