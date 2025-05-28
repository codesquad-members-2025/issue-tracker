import { grayscale, accent } from './color';
import commonTheme from './theme.common';

export const lightTheme = {
  neutral: {
    text: {
      weak: grayscale[600],
      default: grayscale[700],
      strong: grayscale[900],
    },
    surface: {
      default: grayscale[100],
      bold: grayscale[200],
      strong: grayscale[50],
    },
    border: {
      default: grayscale[300],
      active: grayscale[900],
    },
  },
  brand: {
    text: {
      weak: accent.blue,
      default: grayscale[50],
    },
    surface: {
      weak: grayscale[50],
      default: accent.blue,
    },
    border: {
      default: accent.blue,
    },
  },
  danger: {
    text: {
      default: accent.red,
    },
    surface: {
      default: accent.red,
    },
    border: {
      default: accent.red,
    },
  },

  shadow: '0px 8px 16px rgba(20, 20, 43, 0.04)',
  ...commonTheme,
};

export const darkTheme = {
  neutral: {
    text: {
      weak: grayscale[500],
      default: grayscale[400],
      strong: grayscale[50],
    },
    surface: {
      default: grayscale[900],
      bold: grayscale[700],
      strong: grayscale[800],
      weak: grayscale[900],
    },
    border: {
      default: grayscale[600],
      active: grayscale[300],
    },
  },
  brand: {
    text: {
      weak: accent.blue,
      default: grayscale[50],
    },
    surface: {
      weak: grayscale[900],
      default: accent.blue,
    },
    border: {
      default: accent.blue,
    },
  },
  danger: {
    text: {
      default: accent.red,
    },
    surface: {
      default: accent.red,
    },
    border: {
      default: accent.red,
    },
  },

  shadow: '0px 8px 16px rgba(20, 20, 43, 0.8)',
  ...commonTheme,
};

export type ThemeType = typeof lightTheme;
