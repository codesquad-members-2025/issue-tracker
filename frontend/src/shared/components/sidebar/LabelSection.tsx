import styled from '@emotion/styled';
import { useLabels } from '@/features/label/hooks/useLabels';
import { type Label } from '@/features/label/types';
import LabelBadgeList from '@/shared/components/LabelBadgeList';
import Dropdown from '@/shared/components/Dropdown';
import DropdownPanel from '@/shared/components/DropdownPanel';

interface LabelSectionProps {
  selectedLabelIds: number[];
  onToggleLabel: (id: number) => void;
}

export default function LabelSection({
  selectedLabelIds,
  onToggleLabel,
}: LabelSectionProps) {
  const { labels } = useLabels();

  const LabelOptions = labels.map((label: Label) => ({
    id: label.id,
    name: label.name,
    color: label.color,
    selected: selectedLabelIds.includes(label.id),
  }));

  const selectedLabels = labels.filter(label =>
    selectedLabelIds.includes(label.id),
  );

  return (
    <Section>
      <Dropdown label="레이블">
        <DropdownPanel<{ color: string }>
          options={LabelOptions}
          onSelect={onToggleLabel}
          renderOption={option => (
            <LabelInfo>
              <ColorDot style={{ backgroundColor: option.color }} />
              <span>{option.name}</span>
            </LabelInfo>
          )}
        />
      </Dropdown>
      {selectedLabelIds.length > 0 && (
        <LabelList>
          <LabelBadgeList labels={selectedLabels} />
        </LabelList>
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

const LabelInfo = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

const ColorDot = styled.div`
  width: 12px;
  height: 12px;
  border-radius: 50%;
`;

const LabelList = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 4px;

  ${({ theme }) => theme.typography.availableMedium12};
  color: ${({ theme }) => theme.neutral.text.strong};
`;
