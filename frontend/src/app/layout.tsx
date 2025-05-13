import EmotionProvider from "./emotion-provider";
import GlobalStyles from "@/styles/global-styles";
import StyledApp from "./styled-app";

export const metadata = { title: "Issue Tracker" };

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ko">
      <body>
        <EmotionProvider>
          <StyledApp>
            <GlobalStyles />
            {children}
          </StyledApp>
        </EmotionProvider>
      </body>
    </html>
  );
}
