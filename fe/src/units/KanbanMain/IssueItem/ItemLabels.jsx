import styled from 'styled-components';
import Label from '@/base-ui/utils/Label';

const Labels = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
`;

const TooltipWrapper = styled.div`
  position: relative;
  display: inline-block;

  &:hover > span {
    visibility: visible;
    opacity: 1;
  }
`;

const Tooltip = styled.span`
  visibility: hidden;
  opacity: 0;
  width: max-content;
  background-color: ${({ theme }) => theme.text.strong};
  color: ${({ theme }) => theme.surface.weak};
  text-align: center;
  padding: 4px 8px;
  border-radius: 4px;
  position: absolute;
  z-index: 1;
  bottom: 125%;
  left: 50%;
  transform: translateX(-50%);
  transition: opacity 0.2s ease-in-out;
  font-size: 12px;
  white-space: nowrap;
`;

export default function ItemLabels({ labels }) {
  return (
    <>
      {labels && (
        <Labels>
          {labels.map(({ id, color, name, description }) => (
            <TooltipWrapper key={id}>
              <Label id={id} color={color} labelTitle={name} />
              <Tooltip>{description}</Tooltip>
            </TooltipWrapper>
          ))}
        </Labels>
      )}
    </>
  );
}
