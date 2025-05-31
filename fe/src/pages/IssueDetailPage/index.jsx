import styled from 'styled-components';
import useDataFetch from '@/hooks/useDataFetch';
import getOptionWithToken from '@/utils/getOptionWithToken/getOptionWithToken';
import { useEffect, useRef } from 'react';
import DetailIssueHeader from '@/units/issuePage/DetailIssueHeader';
import {
  getDetailIssueAPI,
  patchDetailIssueAPI,
  postCommentInDetailIssueAPI,
  patchCommentInDetailIssueAPI,
} from '@/api/detailIssue';
import { useParams } from 'react-router-dom';
import useIssueDetailStore from '@/stores/IssueDetailStore';
import DisplayComment from '@/units/issuePage/DisplayComment';
import SideBar from '@/units/SideBar';
import NewComment from '@/units/issuePage/NewComment';

export default function IssueDetailPage() {
  const { response, fetchData, isLoading } = useDataFetch({ fetchType: '이슈 상세 페이지' });
  const { id } = useParams();
  const shouldRefetch = useRef(false);
  const initIssueDetailStore = useIssueDetailStore((s) => s.initStore);
  const comments = useIssueDetailStore((s) => s.comments);
  const issue = useIssueDetailStore((s) => s.issue); //메인 코멘트 전용

  async function issueFetchHandler(method, option) {
    const fetchOption = getOptionWithToken(option);
    if (method === 'GET') {
      const { ok } = await fetchData(getDetailIssueAPI(id), fetchOption);
      if (ok) {
        shouldRefetch.current = false;
      }
    } else if (method === 'PATCH') {
      const { ok } = await fetchData(patchDetailIssueAPI(id), fetchOption);
      if (ok) {
        shouldRefetch.current = true;
      }
    }
  }
  async function commentFetchHandler(method, commentId = null, option) {
    const fetchOption = getOptionWithToken(option);
    if (method === 'POST') {
      const { ok } = await fetchData(postCommentInDetailIssueAPI(id), fetchOption);
      if (ok) {
        shouldRefetch.current = true;
      }
    } else if (method === 'PATCH') {
      const { ok } = await fetchData(patchCommentInDetailIssueAPI(id, commentId), fetchOption);
      if (ok) {
        shouldRefetch.current = true;
      }
    }
  }

  // 1. 최초 마운트 시 GET 요청 보냄
  useEffect(() => {
    const GEToptions = {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    };
    issueFetchHandler('GET', GEToptions);
  }, [id]);

  // 2. PATCH 등으로 인해 shouldRefetch가 true가 되면 다시 fetch
  useEffect(() => {
    if (!shouldRefetch.current) return;
    if (!response.success) return;

    const GEToptions = {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    };
    issueFetchHandler('GET', GEToptions);
  }, [response]);

  //실제로 reponse의 data가 변해야 스토어 업데이트
  useEffect(() => {
    if (!response?.data) return;
    initIssueDetailStore(response.data);
  }, [response?.data]);

  if (!response?.data) return null;
  return (
    <Container>
      <DetailIssueHeader issueFetchHandler={issueFetchHandler} />
      <Kanban>
        <CommentsWrapper>
          <DisplayComment
            commentObj={mainCommentMap(issue)}
            commentPatchHandler={issueFetchHandler}
            isMainComment={true}
          />
          {comments.map((comment) => {
            return (
              <DisplayComment
                key={comment.commentId}
                isMainComment={false}
                commentObj={comment}
                commentPatchHandler={commentFetchHandler}
              />
            );
          })}
          <NewComment commentFetchHandler={commentFetchHandler} />
        </CommentsWrapper>
        <SideBar pageType={'detail'} issueFetchHandler={issueFetchHandler} />
      </Kanban>
    </Container>
  );
}

const Container = styled.div`
  padding: 32px 80px 80px 80px;
  display: flex;
  flex-direction: column;
  gap: 24px;
`;

const Kanban = styled.div`
  display: flex;
  justify-content: space-between;
  gap: 32px;
  align-items: flex-start;
`;

const CommentsWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
  width: 960px;
`;

function mainCommentMap(issue) {
  const {
    issueId,
    content,
    issueFileUrl,
    authorNickname,
    lastModifiedAt,
    authorProfileUrl,
    authorId,
  } = issue;
  const commentMap = {
    commentId: issueId,
    content,
    issueFileUrl,
    authorNickname,
    lastModifiedAt,
    profileImageUrl: authorProfileUrl,
    authorId,
  };
  return commentMap;
}
