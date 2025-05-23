import { render, screen } from '@testing-library/react';
import { ThemeProvider } from '@emotion/react';
import Profile from './Profile';
import { lightTheme } from '@/shared/styles/theme';

describe('Profile 컴포넌트', () => {
  const renderWithTheme = (ui: React.ReactElement) =>
    render(<ThemeProvider theme={lightTheme}>{ui}</ThemeProvider>);

  it('imageUrl prop이 있으면 해당 src를 보여준다', () => {
    const url = '/me.png';
    renderWithTheme(<Profile imageUrl={url} />);
    const img = screen.getByRole('img', { name: /profile image/i });
    expect(img).toHaveAttribute('src', url);
  });

  it('imageUrl prop이 없으면 default-profile.png를 보여준다', () => {
    renderWithTheme(<Profile imageUrl="" />);
    const img = screen.getByRole('img', { name: /profile image/i });
    expect(img).toHaveAttribute('src', '/images/sampleProfile.png');
  });

  it('name prop이 있으면 텍스트를 렌더링한다', () => {
    const name = '순원';
    renderWithTheme(<Profile imageUrl="/me.png" name={name} />);
    expect(screen.getByText(name)).toBeInTheDocument();
  });

  it('name prop이 없으면 텍스트 엘리먼트를 렌더링하지 않는다', () => {
    renderWithTheme(<Profile imageUrl="/me.png" />);
    // 닉네임 span이 없으므로 queryByText로 null 확인
    expect(screen.queryByText(/./)).toBeNull();
  });
});
