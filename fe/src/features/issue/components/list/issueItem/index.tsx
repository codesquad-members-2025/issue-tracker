import styled from '@emotion/styled';
import { type Issue } from '../../../types/issue';
import { formatCreatedMessage } from '../../../utils';

import StatusIcon from './StatusIcon';
import TitleWithLabels from './TitleWithLabels';
import MetaInfo from './MetaInfo';
import Assignees from './Assignees';

type IssueItemProps = Omit<Issue, 'updatedAt'>;

const IssueItem = ({
  id,
  author,
  title,
  labels,
  milestone,
  isClosed,
  createdAt,
  assigneesProfileImages,
}: IssueItemProps) => {
  return (
    <IssueItemWrapper>
      <LeftSection>
        <Checkbox type="checkbox" />
        <ContentWrapper>
          <TitleRow>
            <IconWrapper>
              <StatusIcon isClosed={isClosed} />
            </IconWrapper>
            <TitleWithLabels title={title} labels={labels} />
          </TitleRow>
          <MetaInfo
            id={id}
            createdAt={createdAt}
            author={author}
            milestone={milestone}
            formatCreatedMessage={formatCreatedMessage}
          />
        </ContentWrapper>
      </LeftSection>
      <RightSection>
        <Assignees assigneesProfileImages={assigneesProfileImages} />
      </RightSection>
    </IssueItemWrapper>
  );
};

export default IssueItem;

const IssueItemWrapper = styled.li`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 32px;
  border-bottom: 1px solid ${({ theme }) => theme.neutral.border.default};
  background-color: ${({ theme }) => theme.neutral.surface.strong};

  &:last-of-type {
    border-bottom: none;
  }

  &:hover {
    background-color: ${({ theme }) => theme.neutral.surface.default};
  }
`;

const LeftSection = styled.div`
  display: flex;
  gap: 32px;
  flex: 1;
`;

const Checkbox = styled.input`
  display: flex;
  justify-content: center;
  width: 16px;
  height: 32px;
  cursor: pointer;
`;

const ContentWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

const TitleRow = styled.div`
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 8px;
`;

const IconWrapper = styled.span`
  display: flex;
  align-items: center;
  color: ${({ theme }) => theme.palette.blue};

  svg {
    width: 16px;
    height: 16px;
  }
`;

const RightSection = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;
