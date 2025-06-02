import styled from '@emotion/styled';
import { useUsers } from '@/features/user/hooks/useUsers';
import { type User } from '@/features/user/types';
import Profile from '@/shared/components/Profile';
import Dropdown from '@/shared/components/Dropdown';
import DropdownPanel from '@/shared/components/DropdownPanel';

interface AssigneeSectionProps {
  selectedAssigneeIds: number[];
  onToggleAssignee: (id: number) => void;
}

export default function AssigneeSection({
  selectedAssigneeIds,
  onToggleAssignee,
}: AssigneeSectionProps) {
  const { users } = useUsers();

  const assigneeOptions = users.map((user: User) => ({
    id: user.id,
    name: user.nickname,
    imageUrl: user.profileImage,
    selected: selectedAssigneeIds.includes(user.id),
  }));

  const selectedAssignees = users.filter(user =>
    selectedAssigneeIds.includes(user.id),
  );

  return (
    <Section>
      <Dropdown label="담당자">
        <DropdownPanel<{ imageUrl: string }>
          options={assigneeOptions}
          onSelect={onToggleAssignee}
          renderOption={option => (
            <UserInfo>
              <UserImage src={option.imageUrl} />
              <span>{option.name}</span>
            </UserInfo>
          )}
        />
      </Dropdown>
      {selectedAssigneeIds.length > 0 && (
        <SectionList>
          {selectedAssignees.map(assignee => (
            <Profile
              key={assignee.id}
              size="sm"
              name={assignee.nickname}
              imageUrl={assignee.profileImage}
            />
          ))}
        </SectionList>
      )}
    </Section>
  );
}

const Section = styled.div<{ noDivider?: boolean }>`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 16px;
  padding: 32px;
`;

const UserInfo = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

const UserImage = styled.img`
  width: 20px;
  height: 20px;
  border-radius: 50%;
  object-fit: cover;
`;

const SectionList = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 16px;

  ${({ theme }) => theme.typography.availableMedium12};
  color: ${({ theme }) => theme.neutral.text.strong};
`;
