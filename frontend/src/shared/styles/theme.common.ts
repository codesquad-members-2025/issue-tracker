import typography from '@/shared/styles/typography';
import { accent } from '@/shared/styles/color';

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
  spacing: {
    base: {
      xsmall: '4px',
      small: '8px',
      medium: '16px',
      large: '24px',
      xlarge: '32px',
    },
    button: {
      paddingY: {
        ghost: '8px',
        default: '12px',
      },
      width: {
        small: '128px',
        medium: '184px',
        large: '240px',
      },
      gap: {
        small: '4px',
        default: '8px',
      },
    },
  },
};

export default commonTheme;
