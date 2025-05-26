/** @jsxImportSource @emotion/react */
import { type ButtonHTMLAttributes, type ReactNode } from 'react';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import type { ThemeType } from '../styles/theme';

// ✅ 타입 정의
type ButtonVariant = 'contained' | 'outline' | 'ghost';
type ButtonSize = 'large' | 'medium' | 'small';

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: ButtonVariant;
  size?: ButtonSize;
  fullWidth?: boolean;
  icon?: ReactNode;
  selected?: boolean;
  children: ReactNode;
}

interface StyledButtonProps {
  variant: ButtonVariant;
  size: ButtonSize;
  fullWidth: boolean;
  selected: boolean;
}

interface TextWrapperProps {
  variant: ButtonVariant;
  size: ButtonSize;
}

// ✅ Button 컴포넌트 본체
const Button: React.FC<ButtonProps> = ({
  variant = 'contained',
  size = 'medium',
  fullWidth = false,
  icon,
  selected = false,
  children,
  ...rest
}) => {
  return (
    <StyledButton
      type="button"
      variant={variant}
      size={size}
      fullWidth={fullWidth}
      selected={selected}
      {...rest}
    >
      {icon}
      <TextWrapper variant={variant} size={size}>
        {children}
      </TextWrapper>
    </StyledButton>
  );
};

export default Button;

// ✅ 스타일 컴포넌트
const StyledButton = styled.button<StyledButtonProps>`
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: ${({ variant, theme }) =>
    variant === 'ghost' ? theme.spacing.button.gap.small : '0px'};
  cursor: pointer;
  transition:
    background-color 0.15s,
    filter 0.15s;

  ${({ theme, variant }) => getVariantStyle(theme, variant)};
  ${({ theme, variant }) => css`
    padding-top: ${getPaddingY(theme, variant)};
    padding-bottom: ${getPaddingY(theme, variant)};
  `}
  ${({ theme, size, selected }) => getTypography(theme, size, selected)};
  ${({ theme, size, variant, fullWidth }) =>
    getWidthStyle(theme, size, variant, fullWidth)}

  border-radius: ${({ theme }) => theme.radius.medium};

  &:disabled {
    opacity: 0.4;
    cursor: not-allowed;
  }
`;

const TextWrapper = styled.span<TextWrapperProps>`
  padding-inline: ${({ variant, size, theme }) =>
    variant === 'ghost' ? '0px' : getPaddingInline(theme, size)};
`;

// ✅ 유틸 함수 및 상수
const sizeToTypoKey: Record<ButtonSize, keyof ThemeType['typography']> = {
  large: 'availableMedium20',
  medium: 'availableMedium16',
  small: 'availableMedium12',
};

const sizeToSelectedTypoKey: Record<ButtonSize, keyof ThemeType['typography']> =
  {
    large: 'selectedBold20',
    medium: 'selectedBold16',
    small: 'selectedBold12',
  };

const getTypography = (
  theme: ThemeType,
  size: ButtonSize,
  selected: boolean,
) => {
  const key = selected ? sizeToSelectedTypoKey[size] : sizeToTypoKey[size];
  return theme.typography[key];
};

const getWidthStyle = (
  theme: ThemeType,
  size: ButtonSize,
  variant: ButtonVariant,
  fullWidth: boolean,
) => {
  if (fullWidth)
    return css`
      width: 100%;
    `;
  if (variant !== 'ghost') {
    return css`
      width: ${theme.spacing.button.width[size]};
    `;
  }
  return css``;
};

const getPaddingInline = (theme: ThemeType, size: ButtonSize): string => {
  return size === 'small'
    ? theme.spacing.base.xsmall
    : theme.spacing.base.small;
};

const getPaddingY = (theme: ThemeType, variant: ButtonVariant) =>
  variant === 'ghost'
    ? theme.spacing.button.paddingY.ghost
    : theme.spacing.button.paddingY.default;

const getVariantStyle = (theme: ThemeType, variant: ButtonVariant) =>
  variantStyles[variant](theme);

const variantStyles = {
  contained: (theme: ThemeType) => css`
    background-color: ${theme.brand.surface.default};
    color: ${theme.brand.text.default};
    border: none;
    &:hover {
      filter: brightness(0.9);
    }
    &:active {
      filter: brightness(0.8);
    }
  `,
  outline: (theme: ThemeType) => css`
    background-color: transparent;
    color: ${theme.brand.border.default};
    box-shadow: inset 0 0 0 1px ${theme.brand.border.default};
    &:hover {
      background-color: ${theme.neutral.surface.default};
    }
    &:active {
      background-color: ${theme.neutral.surface.bold};
    }
  `,
  ghost: (theme: ThemeType) => css`
    background-color: transparent;
    color: ${theme.neutral.text.default};
    border: none;
    &:hover {
      background-color: ${theme.neutral.surface.default};
    }
    &:active {
      background-color: ${theme.neutral.surface.bold};
    }
  `,
} as const;
