import Button from '@/shared/components/Button';

interface TabItemProps {
  icon: React.ReactNode;
  label: string;
  count: number;
  selected: boolean;
  onClick: () => void;
}

export default function TabItem({
  icon,
  label,
  count,
  selected,
  onClick,
}: TabItemProps) {
  return (
    <Button variant="ghost" icon={icon} selected={selected} onClick={onClick}>
      {`${label}(${count})`}
    </Button>
  );
}
