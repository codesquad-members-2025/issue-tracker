import { useLayoutEffect, useRef, useState } from 'react';
import { createPortal } from 'react-dom';

interface DropdownPortalProps {
  anchorRef: React.RefObject<HTMLElement | null>;
  children: React.ReactNode;
}

export default function DropdownPortal({
  anchorRef,
  children,
}: DropdownPortalProps) {
  const [coords, setCoords] = useState({ x: 0, y: 0 });
  const panelRef = useRef<HTMLDivElement>(null);

  useLayoutEffect(() => {
    if (!anchorRef.current) return;

    const rect = anchorRef.current.getBoundingClientRect();
    setCoords({
      x: rect.right + window.scrollX,
      y: rect.bottom + window.scrollY,
    });
  }, [anchorRef]);

  return createPortal(
    <div
      ref={panelRef}
      style={{
        position: 'absolute',
        transform: `translate(${coords.x}px, ${coords.y}px)`,
        zIndex: 1000,
      }}
    >
      {children}
    </div>,
    document.body,
  );
}
