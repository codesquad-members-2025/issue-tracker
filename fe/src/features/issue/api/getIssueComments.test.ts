import { fetchMocker } from '@/setupTests';
import getIssueComments from './getIssueComments';

describe('getIssueComments', () => {
  beforeEach(() => {
    fetchMocker.resetMocks();
  });

  it('should fetch comments correctly', async () => {
    fetchMocker.mockResponseOnce(
      JSON.stringify({
        comments: [
          {
            id: 1,
            author: {
              id: 10,
              nickname: 'jjinbbangS2',
              profileImage: 'https://example.com/avatar1.png',
            },
            content: '이 이슈에 동의합니다.',
            createdAt: '2025-05-20T10:30:00.000Z',
            updatedAt: '2025-05-20T10:30:00.000Z',
          },
        ],
      }),
      { status: 200 },
    );

    const result = await getIssueComments(101);
    expect(result.comments).toHaveLength(1);
    expect(result.comments[0].author.nickname).toBe('jjinbbangS2');
  });

  it('should throw error when status is 404', async () => {
    fetchMocker.mockResponseOnce('', { status: 404 });

    await expect(() => getIssueComments(999)).rejects.toThrow(
      '요청한 이슈가 존재하지 않습니다',
    );
  });
});
