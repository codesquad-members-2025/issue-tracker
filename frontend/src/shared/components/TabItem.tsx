import { type ReactNode } from 'react';
import Button from '@/shared/components/Button';

interface TabItemProps {
  icon: ReactNode;
  label: string;
  count: number;
  onClick?: () => void;
  isActive?: boolean;
}
export default function TabItem({
  icon,
  label,
  count,
  onClick,
  isActive = false,
}: TabItemProps) {
  return (
    <Button
      variant="ghost"
      fullWidth={true}
      icon={icon}
      selected={isActive}
      onClick={onClick}
    >
      {`${label}(${count})`}
    </Button>
  );
}
