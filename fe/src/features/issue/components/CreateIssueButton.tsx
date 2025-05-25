import { useNavigate } from 'react-router-dom';
import Button from '@/shared/components/Button';
import PlusIcon from '@/assets/icons/plus.svg?react';

export default function CreateIssueButton() {
  const navigate = useNavigate();

  return (
    <Button
      size="small"
      icon={<PlusIcon />}
      onClick={() => navigate('/issues/new')}
    >
      이슈 작성
    </Button>
  );
}
