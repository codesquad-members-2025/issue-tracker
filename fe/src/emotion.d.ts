import '@emotion/react';
import type { ThemeType } from '@/shared/styles/theme';

declare module '@emotion/react' {
  export interface Theme extends ThemeType {}
}
