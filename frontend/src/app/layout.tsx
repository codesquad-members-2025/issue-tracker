"use client";

import EmotionProvider from "../styles/emotionProvider";
import GlobalStyles from "@/styles/globalStyles";
import ThemeProvider from "@components/theme/ThemeProvider";
import ClientProviders from "@/components/ClientProviders";

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ko">
      <body suppressHydrationWarning>
        <ClientProviders>
          {children}
        </ClientProviders>
      </body>
    </html>
  );
}
