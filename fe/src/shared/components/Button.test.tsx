import { render, screen } from '@testing-library/react';
import { ThemeProvider } from '@emotion/react';
import userEvent from '@testing-library/user-event';
import { lightTheme } from '@/shared/styles/theme';
import Button from './Button';

const OUTLINE_BORDER_COLOR = lightTheme.brand.border.default;

describe('Button 컴포넌트', () => {
  const renderWithTheme = (ui: React.ReactElement) =>
    render(<ThemeProvider theme={lightTheme}>{ui}</ThemeProvider>);

  it('children 텍스트가 표시되어야 한다', () => {
    renderWithTheme(<Button>확인</Button>);
    const btn = screen.getByRole('button');
    expect(btn).toHaveTextContent('확인');
  });

  it('variant가 outline일 경우 border가 있어야 한다', () => {
    renderWithTheme(<Button variant="outline">테스트</Button>);
    const btn = screen.getByRole('button');
    expect(btn).toHaveStyle(
      `box-shadow: inset 0 0 0 1px ${OUTLINE_BORDER_COLOR}`,
    );
  });

  it('fullWidth이면 width가 100%여야 한다', () => {
    renderWithTheme(<Button fullWidth>전체 너비</Button>);
    const btn = screen.getByRole('button');
    expect(btn).toHaveStyle('width: 100%');
  });

  it('disabled면 클릭되지 않아야 한다', async () => {
    const onClick = vi.fn();
    renderWithTheme(
      <Button disabled onClick={onClick}>
        비활성화
      </Button>,
    );
    const btn = screen.getByRole('button');
    await userEvent.click(btn);
    expect(onClick).not.toHaveBeenCalled();
  });

  it('selected면 bold체가 되어야 한다', () => {
    renderWithTheme(<Button selected={true}>선택됨</Button>);
    const btn = screen.getByRole('button');
    expect(btn).toHaveStyle('font-weight: 700');
  });
});
