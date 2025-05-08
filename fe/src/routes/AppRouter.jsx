import { BrowserRouter, Routes, Route } from 'react-router-dom';

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        {/* 로그인/회원가입은 레이아웃 없이 단독 페이지 */}
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signUp" element={<SignUpPage />} />

        {/* 로그인된 유저만 접근 가능한 경로 */}
        <Route element={<PrivateRoute />}>
          <Route path="/" element={<MainLayout />}>
            <Route index element={<IssueListPage />} />
            <Route path="new" element={<NewIssuePage />} />
            <Route path="labels" element={<LabelPage />} />
            <Route path="milestones" element={<MilestonePage />} />
            <Route path=":id" element={<IssueDetailPage />} />
            <Route path="*" element={<NotFoundPage />} />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
