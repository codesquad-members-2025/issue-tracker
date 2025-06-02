import { postNewIssue } from '@/features/newIssue/apis/postNewIssue';
import { fetchMocker } from '@/setupTests';

describe('postNewIssue', () => {
  beforeEach(() => {
    fetchMocker.resetMocks();
  });

  const payload = {
    title: '이슈 제목',
    content: '내용입니다',
    assignees: [1],
    labels: [2],
    milestone: 3,
  };

  it('201 응답 시, 응답 데이터를 반환해야 한다', async () => {
    const mockResponse = { id: 101, ...payload };

    fetchMocker.mockResponseOnce(JSON.stringify(mockResponse), {
      status: 201,
    });

    const result = await postNewIssue(payload);

    expect(result).toEqual(mockResponse);
  });

  it('401 응답 시, 로그인 필요 에러를 발생시켜야 한다', async () => {
    fetchMocker.mockResponseOnce('', { status: 401 });

    await expect(postNewIssue(payload)).rejects.toThrow('로그인이 필요합니다');
  });

  it('404 응답 시, 경로 오류 에러를 발생시켜야 한다', async () => {
    fetchMocker.mockResponseOnce('', { status: 404 });

    await expect(postNewIssue(payload)).rejects.toThrow(
      '요청 경로가 잘못되었습니다',
    );
  });

  it('500 응답 시, 서버 오류 에러를 발생시켜야 한다', async () => {
    fetchMocker.mockResponseOnce('', { status: 500 });

    await expect(postNewIssue(payload)).rejects.toThrow(
      '서버 오류가 발생했습니다',
    );
  });

  it('정의되지 않은 상태 코드 응답 시, 기본 에러를 발생시켜야 한다', async () => {
    fetchMocker.mockResponseOnce('', { status: 418 });

    await expect(postNewIssue(payload)).rejects.toThrow(
      '예상치 못한 오류가 발생했습니다 (status: 418)',
    );
  });
});
