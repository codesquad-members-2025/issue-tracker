import { useState } from 'react';
import * as S from './DropdownMenuTemplate.styles.js';

export function DropdownMenuTemplate({
  triggerLabel = 'Select',
  menuWidth = '12rem',
  label,
  groups = [],
}) {
  const [open, setOpen] = useState(false);

  return (
    <S.Container>
      <S.TriggerButton onClick={() => setOpen((prev) => !prev)}>{triggerLabel}</S.TriggerButton>

      {open && (
        <S.Menu $width={menuWidth}>
          {label && <S.Label>{label}</S.Label>}
          {label && <S.Separator />}
          {groups.map((group, groupIdx) => (
            <S.Group key={groupIdx}>
              {group.items.map((item, idx) =>
                item.type === 'separator' ? (
                  <S.Separator key={`sep-${idx}`} />
                ) : (
                  <S.Item key={idx} onClick={item.onClick} disabled={item.disabled}>
                    {item.icon && <span style={{ marginRight: '0.5rem' }}>{item.icon}</span>}
                    {item.label}
                    {item.shortcut && <S.Shortcut>{item.shortcut}</S.Shortcut>}
                  </S.Item>
                ),
              )}
              {groupIdx < groups.length - 1 && <S.Separator />}
            </S.Group>
          ))}
        </S.Menu>
      )}
    </S.Container>
  );
}
