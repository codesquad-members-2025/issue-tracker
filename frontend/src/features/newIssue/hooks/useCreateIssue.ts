import { useNavigate } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';
import { postNewIssue } from '../apis/postNewIssue';
import type { NewIssuePayload, PostNewIssueResponse } from '../types';

export const useCreateIssue = () => {
  const navigate = useNavigate();

  return useMutation<PostNewIssueResponse, Error, NewIssuePayload>({
    mutationFn: postNewIssue,

    onSuccess: data => {
      const issueId = data.id;
      console.log('이슈 생성 성공:', issueId);
      navigate(`/issues/${issueId}`);
    },

    onError: error => {
      console.error('이슈 생성 실패:', error);
    },
  });
};
