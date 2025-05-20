import typography from './typography';
import { accent } from './color';

const commonTheme = {
  typography,
  radius: {
    half: '50%',
    medium: '12px',
    large: '16px',
  },
  borderStyle: {
    default: '1px solid',
    icon: '1.6px solid',
    dash: '1px dashed',
  },
  opacity: {
    hover: 0.8,
    press: 0.64,
    disabled: 0.32,
  },
  palette: {
    blue: accent.blue,
    navy: accent.navy,
    red: accent.red,
  },
};

export default commonTheme;
