import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';
import { someHandler } from '@/utils/common/someHandler';

const useIssueDetailStore = create(
  immer((set) => ({
    // issue 프로퍼티 -> 서버로부터 DB에 있는 데이터를 보내줄때 업데이트하는 용도입니다.
    issue: {
      issueId: '',
      title: '',
      issueFileUrl: [],
      authorId: null,
      milestoneId: null,
      isOpen: '',
      lastModifiedAt: '',
    },

    //입력 액션이 발생하면 계속해서 변하는 필드
    comment: { commentId: null, content: '', issueFileUrl: [] },

    /* 
    - 아래의 프로퍼티 4개는 서버로부터 데이터를 받을때(*이슈 상세페이지_이슈가 이미 존재할경우*) 업데이트 됩니다.
    - 새로운 이슈 작성할 경우(*이슈 작성 페이지*) 사용자가 선택한 옵션이 아래 프로퍼티에 업데이트 됩니다. -> 아래의 프로퍼티를 그대로 서버에 POST요청을 보냅니다.
    - (*이슈 상세 페이지*)이 상황에서는 서버에 PATCH 요청을 보낼때 아래의 프로퍼티를 보냅니다.
    */
    assignees: [],
    labels: [],
    milestone: {},
    comments: [],

    //클릭하는 순간 적용
    toggleAssignee: (item) => {
      set((state) => {
        const assignees = state.assignees;
        if (someHandler(assignees, item.id)) {
          state.assignees = assignees.filter(({ id }) => id !== item.id);
        } else {
          state.assignees.push(item);
        }
      });
    },

    toggleLabel: (item) => {
      set((state) => {
        const labels = state.labels;
        if (someHandler(labels, item.id)) {
          state.labels = labels.filter(({ id }) => id !== item.id);
        } else {
          state.labels.push(item);
        }
      });
    },

    toggleMilestone: (item) => {
      set((state) => {
        const milestone = state.milestone;
        if (milestone?.id === item.id) {
          state.milestone = {};
          state.issue.milestoneId = null;
        } else {
          state.milestone = item;
          state.issue.milestoneId = item.id;
        }
      });
    },

    changeTitle: (value) => {
      set((state) => {
        state.issue.title = value;
      });
    },

    addOrEditComment: (value, commentId = null) => {
      set((state) => {
        if (commentId) {
          // 편집 모드: comments에서 해당 객체로 교체(초기화), 이후 state.comment에 입력값 적용
          const target = state.comments.find((comment) => comment.commentId === commentId);
          state.comment = target;
        }
        state.comment = {
          commentId: null,
          content: value,
          issueFileUrl: [],
        };
      });
    },

    // 상세 이슈페이지 GET 패치 후 초기화 로직
    initComments: (comments) => {
      set((state) => {
        state.comments = comments;
      });
    },

    //검토 필요
    setFiles: (newFiles) =>
      set((state) => {
        const map = new Map();
        state.comment.issueFileUrl.forEach((f) => map.set(f.name, f));
        newFiles.forEach((f) => map.set(f.name, f));
        state.comment.issueFileUrl = Array.from(map.values());
      }),
  })),
);

export default useIssueDetailStore;
