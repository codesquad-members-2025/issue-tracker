import VerticalStack from '@/layouts/VerticalStack';
import Divider from '@/shared/components/Divider';
import NewIssueHeader from '@/features/newIssue/components/NewIssueHeader';
import NewIssueFormSection from '@/features/newIssue/components/NewIssueFormSection';
import NewIssueActionButtons from '@/features/newIssue/components/NewIssueActionButtons';

export default function NewIssuePage() {
  return (
    <VerticalStack>
      <NewIssueHeader />
      <Divider />
      <NewIssueFormSection />
      <Divider />
      <NewIssueActionButtons />
    </VerticalStack>
  );
}
