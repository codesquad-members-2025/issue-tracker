// 클라이언트 전용 로직은 별도 Client Component로 분리
// EmotionProvider, ThemeProvider, GlobalStyles와 같이 내부에서 브라우저 API를 쓰거나 useEffect를 사용하는 부분만 분리
"use client";
import EmotionProvider from "../styles/emotionProvider";
import ThemeProvider from "@/components/ThemeProvider";
import GlobalStyles from "@/styles/globalStyles";

export default function ClientProviders({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <EmotionProvider>
      <ThemeProvider>
        <GlobalStyles />
        {children}
      </ThemeProvider>
    </EmotionProvider>
  );
}
