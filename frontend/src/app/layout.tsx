"use client";

import EmotionProvider from "../styles/emotionProvider";
import GlobalStyles from "@/styles/globalStyles";
import ThemeProvider from "@/components/ThemeProvider";

// export const metadata = {
//   title: "Issue Tracker",
//   description: "…",
// };

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ko">
      <body>
        <EmotionProvider>
          <ThemeProvider>
            <GlobalStyles />
            {children}
          </ThemeProvider>
        </EmotionProvider>
      </body>
    </html>
  );
}
