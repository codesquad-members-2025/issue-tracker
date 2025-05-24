import getIssueDetail from './getIssueDetail';
import { fetchMocker } from '@/setupTests';

describe('getIssueDetail', () => {
  it('should return issue detail when response is 200', async () => {
    const mockData = { id: 1, title: 'Test Issue' };
    fetchMocker.mockResponseOnce(JSON.stringify(mockData));

    const result = await getIssueDetail(1);
    expect(result).toEqual(mockData);
  });

  it('should throw 401 error message', async () => {
    fetchMocker.mockResponseOnce('', { status: 401 });

    await expect(getIssueDetail(1)).rejects.toThrow('로그인이 필요합니다');
  });

  it('should throw 404 error message', async () => {
    fetchMocker.mockResponseOnce('', { status: 404 });

    await expect(getIssueDetail(1)).rejects.toThrow(
      '요청한 이슈가 존재하지 않습니다',
    );
  });

  it('should throw 500 error message', async () => {
    fetchMocker.mockResponseOnce('', { status: 500 });

    await expect(getIssueDetail(1)).rejects.toThrow('서버 오류가 발생했습니다');
  });

  it('should throw fallback error for unknown status', async () => {
    fetchMocker.mockResponseOnce('', { status: 403 });

    await expect(getIssueDetail(1)).rejects.toThrow(
      '예상치 못한 오류가 발생했습니다 (status: 403)',
    );
  });
});
