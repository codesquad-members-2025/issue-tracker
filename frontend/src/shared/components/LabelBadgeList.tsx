import LabelBadge from './LabelBadge';
import { getAccessibleLabelStyle } from '@/shared/utils/color';
import { type Label } from '@/features/label/types';

interface Props {
  labels: Label[];
}

export default function LabelBadgeList({ labels }: Props) {
  return (
    <>
      {labels.map(label => {
        const { textColor, borderColor } = getAccessibleLabelStyle(label.color);
        return (
          <LabelBadge
            key={label.id}
            name={label.name}
            color={label.color}
            textColor={textColor}
            borderColor={borderColor}
          />
        );
      })}
    </>
  );
}
