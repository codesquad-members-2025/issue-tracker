import { grayscale, accent } from './color';
import commonTheme from './theme.common';

export const lightTheme = {
  textColor: {
    weak: grayscale[600],
    default: grayscale[700],
    strong: grayscale[900],
  },
  surfaceColor: {
    default: grayscale[100],
    bold: grayscale[200],
    strong: grayscale[50],
    weak: grayscale[50],
  },
  borderColor: {
    default: grayscale[300],
    active: grayscale[900],
  },
  brandColor: {
    textWeak: accent.blue,
    default: accent.blue,
  },
  dangerColor: {
    text: accent.red,
    surface: accent.red,
    border: accent.red,
  },
  shadow: '0px 8px 16px rgba(20, 20, 43, 0.04)',

  ...commonTheme,
};

export const darkTheme = {
  textColor: {
    weak: grayscale[500],
    default: grayscale[400],
    strong: grayscale[50],
  },
  surfaceColor: {
    default: grayscale[900],
    bold: grayscale[700],
    strong: grayscale[800],
    weak: grayscale[900],
  },
  borderColor: {
    default: grayscale[600],
    active: grayscale[300],
  },
  brandColor: {
    textWeak: accent.blue,
    default: accent.blue,
  },
  dangerColor: {
    text: accent.red,
    surface: accent.red,
    border: accent.red,
  },
  shadow: '0px 8px 16px rgba(20, 20, 43, 0.8)',

  ...commonTheme,
};

export type ThemeType = typeof lightTheme;
