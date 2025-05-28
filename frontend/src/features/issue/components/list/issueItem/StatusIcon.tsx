import OpenIcon from '@/assets/icons/archive.svg?react';
import ClosedIcon from '@/assets/icons/alertCircle.svg?react';

interface Props {
  isClosed: boolean;
}

const StatusIcon = ({ isClosed }: Props) => {
  return <>{isClosed ? <OpenIcon /> : <ClosedIcon />}</>;
};

export default StatusIcon;
