import styled from '@emotion/styled';
import { useLabels } from '@/features/label/hooks/useLabels';
import { type Label } from '../../label/types';
import { getAccessibleLabelStyle } from '@/shared/utils/color';
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
        <LabelList>{renderLabelList(selectedLabels)}</LabelList>
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

//TODO 공용 라벨 컴포넌트 분리
interface LabelTagProps {
  backgroundColor: string;
  borderColor: string;
  color: string;
}

const LabelTag = styled.span<LabelTagProps>`
  padding: 4px 9px;
  border-radius: ${({ theme }) => theme.radius.medium};
  color: ${({ color }) => color};
  background-color: ${({ backgroundColor }) => backgroundColor};
  border: 1px solid ${({ borderColor }) => borderColor};
  ${({ theme }) => theme.typography.displayMedium12};
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

//TODO 공용 분리
function renderLabelList(labels: Label[]) {
  return labels.map(label => {
    const { textColor, borderColor } = getAccessibleLabelStyle(label.color);
    return (
      <LabelTag
        key={label.id}
        backgroundColor={label.color}
        borderColor={borderColor}
        color={textColor}
      >
        {label.name}
      </LabelTag>
    );
  });
}
