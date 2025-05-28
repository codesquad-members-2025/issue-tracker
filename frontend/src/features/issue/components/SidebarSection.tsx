import styled from '@emotion/styled';
import PlusIcon from '@/assets/icons/plus.svg?react';
import { type ReactNode } from 'react';

interface SidebarSectionProps {
  title: string;
  children: ReactNode;
  isEmpty: boolean;
}

export default function SidebarSection({
  title,
  children,
  isEmpty,
}: SidebarSectionProps) {
  return (
    <Section>
      <SectionHeader>
        {title}
        <PlusIcon />
      </SectionHeader>
      {!isEmpty && children}
    </Section>
  );
}

const Section = styled.div<{ noDivider?: boolean }>`
  display: flex;
  flex-direction: column;
  gap: 16px;
  padding: 32px;
`;

const SectionHeader = styled.div`
  display: flex;
  justify-content: space-between;
  gap: 4px;
  ${({ theme }) => theme.typography.availableMedium16};
  color: ${({ theme }) => theme.neutral.text.default};
`;
