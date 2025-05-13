const ISSUES_MOCK = {
  issues: [
    {
      id: 1,
      author: '완자',
      title: '버튼 클릭 시 이벤트가 발생하지 않아요',
      labels: [
        {
          id: 2,
          name: 'bug',
          color: '#d73a4a',
          description: '버그 관련 이슈',
        },
        {
          id: 3,
          name: 'urgent',
          color: '#e99695',
          description: '긴급한 이슈',
        },
      ],
      milestone: 'v1.0 배포',
      isClosed: false,
      createdAt: '2025-05-01T10:00:00Z',
      updatedAt: '2025-05-10T15:30:00Z',
      assigneesProfileImages: [
        'https://picsum.photos/id/237/200/300',
        'https://picsum.photos/id/237/200/300',
        'https://picsum.photos/id/237/200/300',
      ],
    },
    {
      id: 2,
      author: '소라',
      title: 'UI 개선 제안',
      labels: [
        {
          id: 4,
          name: 'enhancement',
          color: '#a2eeef',
          description: '기능 향상',
        },
      ],
      milestone: 'v1.1 디자인 리뉴얼',
      isClosed: true,
      createdAt: '2025-04-25T09:15:00Z',
      updatedAt: '2025-04-30T14:20:00Z',
      assigneesProfileImages: [
        'https://picsum.photos/id/237/200/300',
        'https://picsum.photos/id/237/200/300',
        'https://picsum.photos/id/237/200/300',
        'https://picsum.photos/id/237/200/300',
        'https://picsum.photos/id/237/200/300',
        'https://picsum.photos/id/237/200/300',
        'https://picsum.photos/id/237/200/300',
      ],
    },
    {
      id: 3,
      author: '감자',
      title: '아이콘 누락',
      labels: [],
      milestone: null,
      isClosed: true,
      createdAt: '2025-04-25T09:15:00Z',
      updatedAt: null,
      assigneesProfileImages: [
        'https://picsum.photos/id/237/200/300',
        'https://picsum.photos/id/237/200/300',
        'https://picsum.photos/id/237/200/300',
      ],
    },
  ],
  openCount: 1,
  closeCount: 1,
};

export default ISSUES_MOCK;
