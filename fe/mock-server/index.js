import express from 'express';
import fs from 'fs/promises';
import cors from 'cors';
import path from 'path';
import { fileURLToPath } from 'url';
import multer from 'multer';
import fsSync from 'fs';

const app = express();
const PORT = 8080;
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// 파일 업로드 경로 설정
const uploadDir = path.join(__dirname, 'uploads');
if (!fsSync.existsSync(uploadDir)) fsSync.mkdirSync(uploadDir);

// multer 설정 (files key)
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, uploadDir);
  },
  filename: function (req, file, cb) {
    // 중복 방지: timestamp+원본이름
    const uniqueSuffix =
      Date.now() + '-' + Buffer.from(file.originalname, 'latin1').toString('utf8');
    cb(null, uniqueSuffix);
  },
});
const upload = multer({ storage, limits: { fileSize: 30 * 1024 * 1024 } }); // 30MB 제한

app.use(
  cors({
    origin: 'http://localhost:5173',
    credentials: true,
  }),
);
app.use(express.json());
// multipart/form-data 전용 요청은 multer에서 파싱, JSON 요청만 express.json

// 인증 미들웨어
const validTokens = ['test-token-123', 'sampleToken123']; // 실제 프로젝트에서는 DB 기반 검증 사용
function authMiddleware(req, res, next) {
  const authHeader = req.headers.authorization;
  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return res.status(401).json({ success: false, message: '인증 토큰이 없습니다.' });
  }
  const token = authHeader.split(' ')[1];
  if (!validTokens.includes(token)) {
    return res.status(403).json({ success: false, message: '유효하지 않은 토큰입니다.' });
  }
  next();
}

// 기본 응답 구조
const createResponse = (success, message, data) => ({
  success,
  message,
  data,
});

app.post('/issues', upload.single('files'), authMiddleware, async (req, res) => {
  try {
    // req.body.data는 json 문자열
    const data = JSON.parse(req.body.data);

    // 파일 처리
    let fileUrl = null;
    if (req.file) {
      fileUrl = `http://localhost:${PORT}/uploads/${req.file.filename}`;
    }

    const filePath = path.join(__dirname, 'mainPage.json');
    const json = JSON.parse(await fs.readFile(filePath, 'utf-8'));

    // 새 이슈 ID 생성
    const newIssueId = Math.max(...json.issues.map((i) => i.id)) + 1;

    // 새 이슈 객체 생성
    const newIssue = {
      issue: {
        issueId: newIssueId,
        title: data.title,
        content: data.content,
        authorId: 1, // 현재 로그인한 사용자 ID
        milestoneId: data.milestoneId,
        isOpen: true,
        lastModifiedAt: new Date().toISOString(),
        issueFileUrl: fileUrl,
      },
      assignees: (data.assigneeIds || [])
        .map((id) => {
          const user = json.users.find((u) => u.id === id);
          return user
            ? {
                id: user.id,
                nickname: user.nickname,
                profileImageUrl:
                  user.profileImageUrl || `https://dummy.local/profile/${user.nickname}.png`,
              }
            : null;
        })
        .filter(Boolean),
      labels: (data.labelIds || [])
        .map((id) => {
          const label = json.labels.find((l) => l.labelId === id);
          return label
            ? {
                labelId: label.labelId,
                name: label.name,
                color: label.color,
              }
            : null;
        })
        .filter(Boolean),
      milestone: data.milestoneId
        ? {
            ...json.milestones.find((m) => m.id === data.milestoneId),
            milestoneId: data.milestoneId,
            processingRate: 0,
          }
        : null,
      comments: [],
    };

    // 새 이슈를 기존 이슈 목록에 추가
    json.issues.push({
      id: newIssueId,
      title: data.title,
      content: data.content,
      isOpen: true,
      author: json.users.find((u) => u.id === 1),
      assignees: newIssue.assignees,
      labels: newIssue.labels,
      milestone: newIssue.milestone,
      createdAt: new Date().toISOString(),
      issueFileUrl: fileUrl,
      comments: [],
    });

    // 파일 저장
    await fs.writeFile(filePath, JSON.stringify(json, null, 2), 'utf-8');

    res.status(201).json(
      createResponse(true, '새 이슈가 생성되었습니다.', {
        issue: {
          issueId: newIssueId,
        },
      }),
    );
  } catch (error) {
    console.error('🔥 이슈 생성 오류:', error.message);
    res.status(500).json(
      createResponse(false, '이슈 생성 중 서버 오류 발생', {
        error: error.message,
      }),
    );
  }
});
app.use('/uploads', express.static(uploadDir));

app.get('/', authMiddleware, async (req, res) => {
  try {
    const { author, label, milestone, assignee, page = 1, limit = 10 } = req.query;

    const filePath = path.join(__dirname, 'mainPage.json');
    const json = JSON.parse(await fs.readFile(filePath, 'utf-8'));
    let issues = json.issues;

    // 필터링
    if (author) {
      issues = issues.filter((i) => String(i.author.id) === String(author));
    }

    if (label) {
      issues = issues.filter((i) => i.labels?.some((l) => String(l.labelId) === String(label)));
    }

    if (milestone) {
      issues = issues.filter((i) => String(i.milestone?.milestoneId) === String(milestone));
    }

    if (assignee) {
      issues = issues.filter((i) => i.assignees?.some((a) => String(a.id) === String(assignee)));
    }
    if (req.query.isOpen !== undefined) {
      const isOpen = req.query.isOpen === 'true';
      issues = issues.filter((i) => i.isOpen === isOpen);
    }

    // 페이지네이션
    const pageNum = parseInt(page, 10);
    const limitNum = parseInt(limit, 10);
    const totalCount = issues.length;
    const totalPages = Math.ceil(totalCount / limitNum);
    const startIndex = (pageNum - 1) * limitNum;
    const paginatedIssues = issues.slice(startIndex, startIndex + limitNum);
    // id → issueId 변환 (응답 전용)
    const formattedIssues = paginatedIssues.map(({ id, ...rest }) => ({
      issueId: id,
      ...rest,
    }));

    // Create a filtered list of issues that ignore isOpen filter
    const baseFilteredIssues = json.issues.filter((i) => {
      if (author && String(i.author.id) !== String(author)) return false;
      if (label && !i.labels?.some((l) => String(l.labelId) === String(label))) return false;
      if (milestone && String(i.milestone?.milestoneId) !== String(milestone)) return false;
      if (assignee && !i.assignees?.some((a) => String(a.id) === String(assignee))) return false;
      return true;
    });

    // Then use that to compute open/close issue numbers
    res.json(
      createResponse(true, '요청에 성공했습니다.', {
        issues: formattedIssues,
        users: json.users,
        labels: json.labels,
        milestones: json.milestones,
        metaData: {
          currentPage: pageNum,
          openIssueNumber: baseFilteredIssues.filter((i) => i.isOpen === true).length,
          closeIssueNumber: baseFilteredIssues.filter((i) => i.isOpen === false).length,
        },
      }),
    );
  } catch (error) {
    console.error('🔥 서버 오류:', error.message);
    res.status(500).json(
      createResponse(false, '서버 내부 오류 발생', {
        error: error.message,
      }),
    );
  }
});

app.post('/login', async (req, res) => {
  try {
    const { loginId, password } = req.body;
    const filePath = path.join(__dirname, 'mainPage.json');
    const json = JSON.parse(await fs.readFile(filePath, 'utf-8'));

    const user = json.users.find((u) => u.nickname === loginId);
    if (!user) {
      return res.status(401).json({
        success: false,
        message: '존재하지 않는 회원입니다.',
      });
    }

    // 목 환경에서는 모든 사용자의 패스워드를 '1234'로 고정
    if (password !== '1234') {
      return res.status(401).json({
        success: false,
        message: '비밀번호가 일치하지 않습니다.',
      });
    }

    // JWT-like access token payload
    const payload = {
      sub: user.id,
      loginId: user.id,
      profileImageUrl: user.profileImageUrl,
      iat: Math.floor(Date.now() / 1000),
      exp: Math.floor(Date.now() / 1000) + 3600,
    };

    const base64Payload = Buffer.from(JSON.stringify(payload)).toString('base64url');
    const accessToken = `header.${base64Payload}.signature`;
    const refreshToken = `refresh.header.${base64Payload}.signature`;

    validTokens.push(accessToken);

    res.json({
      success: true,
      message: '로그인을 성공했습니다.',
      data: {
        accessToken,
        refreshToken,
      },
    });
  } catch (err) {
    console.error('🔥 로그인 오류:', err.message);
    res.status(500).json({
      success: false,
      message: '서버 오류',
      error: err.message,
    });
  }
});

app.patch('/toggleStatus', async (req, res) => {
  try {
    const { id: ids } = req.body; // id 배열을 받음
    if (!Array.isArray(ids)) {
      return res.status(400).json({ success: false, message: 'id 필드는 배열이어야 합니다.' });
    }

    const filePath = path.join(__dirname, 'mainPage.json');
    const json = JSON.parse(await fs.readFile(filePath, 'utf-8'));

    let updatedCount = 0;
    const updatedIssues = json.issues.map((issue) => {
      if (ids.includes(issue.id)) {
        updatedCount++;
        return { ...issue, isOpen: !issue.isOpen };
      }
      return issue;
    });

    const updatedJson = { ...json, issues: updatedIssues };
    await fs.writeFile(filePath, JSON.stringify(updatedJson, null, 2), 'utf-8');

    res.json({
      success: true,
      message: `${updatedCount}개의 이슈 상태가 변경되었습니다.`,
    });
  } catch (error) {
    console.error('🔥 PATCH 오류:', error.message);
    res.status(500).json({
      success: false,
      message: '이슈 상태 변경 중 서버 오류 발생',
      error: error.message,
    });
  }
});

// 코멘트 수정
app.patch('/issues/:issueId/comments/:commentId', upload.single('files'), async (req, res) => {
  try {
    const { issueId, commentId } = req.params;
    // data: JSON 문자열
    const data = req.body.data ? JSON.parse(req.body.data) : {};

    const filePath = path.join(__dirname, 'mainPage.json');
    const json = JSON.parse(await fs.readFile(filePath, 'utf-8'));

    const issue = json.issues.find((issue) => issue.id === Number(issueId));
    if (!issue) {
      return res.status(404).json({
        success: false,
        message: '이슈를 찾을 수 없습니다.',
      });
    }

    const comment = issue.comments?.find((comment) => comment.commentId === Number(commentId));
    if (!comment) {
      return res.status(404).json({
        success: false,
        message: '코멘트를 찾을 수 없습니다.',
      });
    }

    // 파일 URL 처리 (없으면 기존 URL 유지)
    let fileUrl = comment.issueFileUrl || null;
    if (req.file) {
      fileUrl = `http://localhost:${PORT}/uploads/${req.file.filename}`;
    }

    // 코멘트 내용/파일 업데이트
    if (data.content !== undefined) {
      comment.content = data.content;
    }
    comment.issueFileUrl = fileUrl;
    comment.lastModifiedAt = new Date().toISOString();

    // 파일에 변경사항 저장
    await fs.writeFile(filePath, JSON.stringify(json, null, 2), 'utf-8');

    res.json({
      success: true,
      message: '코멘트가 성공적으로 수정되었습니다.',
      data: {
        comment: comment,
      },
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: '코멘트 수정 중 서버 오류 발생',
      error: error.message,
    });
  }
});

// 코멘트 생성 (FormData: data(JSON), files)
app.post('/issues/:issueId/comments', upload.single('files'), async (req, res) => {
  try {
    // data: JSON 문자열
    const data = JSON.parse(req.body.data);
    const { issueId } = req.params;

    const filePath = path.join(__dirname, 'mainPage.json');
    const json = JSON.parse(await fs.readFile(filePath, 'utf-8'));

    const issue = json.issues.find((issue) => issue.id === Number(issueId));
    if (!issue) {
      return res.status(404).json({
        success: false,
        message: '이슈를 찾을 수 없습니다.',
      });
    }

    // 새 코멘트 ID 생성
    const newCommentId =
      Math.max(
        ...json.issues.flatMap(
          (issue) => issue.comments?.map((comment) => comment.commentId) || [0],
        ),
      ) + 1;

    // 파일 URL 처리
    let fileUrl = null;
    if (req.file) {
      fileUrl = `http://localhost:${PORT}/uploads/${req.file.filename}`;
    }

    // 새 코멘트 객체 생성
    const newComment = {
      commentId: newCommentId,
      content: data.content,
      issueFileUrl: fileUrl,
      authorNickname: 'devchan', // 현재는 고정값 사용
      lastModifiedAt: new Date().toISOString(),
    };

    // 이슈의 comments 배열이 없으면 생성
    if (!issue.comments) {
      issue.comments = [];
    }

    // 새 코멘트 추가
    issue.comments.push(newComment);

    // 파일에 변경사항 저장
    await fs.writeFile(filePath, JSON.stringify(json, null, 2), 'utf-8');

    res.status(201).json({
      success: true,
      message: '코멘트가 성공적으로 생성되었습니다.',
      data: {
        comment: newComment,
      },
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: '코멘트 생성 중 서버 오류 발생',
      error: error.message,
    });
  }
});

// PATCH /issues/:id - 이슈 수정
app.patch('/issues/:id', upload.single('files'), authMiddleware, async (req, res) => {
  try {
    const issueId = parseInt(req.params.id);
    const filePath = path.join(__dirname, 'mainPage.json');
    const data = await fs.readFile(filePath, 'utf-8');
    const json = JSON.parse(data);

    const issueIndex = json.issues.findIndex((issue) => issue.id === issueId);
    if (issueIndex === -1) {
      return res.status(404).json({
        success: false,
        message: '이슈를 찾을 수 없습니다.',
      });
    }

    // 멀티파트/폼데이터로 온 경우 data 필드에서 파싱
    let updateData = {};
    if (req.body.data) {
      // FormData: data(JSON) + files
      updateData = JSON.parse(req.body.data);
    } else {
      // application/json
      updateData = req.body;
    }

    let issue = json.issues[issueIndex];

    // 파일 처리: 있으면 덮어씀
    if (req.file) {
      const fileUrl = `http://localhost:${PORT}/uploads/${req.file.filename}`;
      issue.issueFileUrl = fileUrl;
    }

    // 업데이트 할 필드들 반영
    Object.keys(updateData).forEach((key) => {
      if (key === 'assigneeId' && Array.isArray(updateData.assigneeId)) {
        issue.assignees = updateData.assigneeId
          .map((id) => json.users.find((user) => user.id === id))
          .filter(Boolean);
      } else if (key === 'labelId' && Array.isArray(updateData.labelId)) {
        issue.labels = updateData.labelId
          .map((id) => {
            const label = json.labels.find((l) => l.labelId === id);
            return label
              ? {
                  labelId: label.labelId,
                  name: label.name,
                  color: label.color,
                  description: label.description || '',
                }
              : null;
          })
          .filter(Boolean);
      } else if (key === 'milestoneId') {
        const milestone = json.milestones.find((m) => m.milestoneId === updateData.milestoneId);
        issue.milestone = milestone || null;
      } else if (key in issue) {
        issue[key] = updateData[key];
      }
    });

    // 갱신된 이슈 저장
    json.issues[issueIndex] = issue;
    await fs.writeFile(filePath, JSON.stringify(json, null, 2), 'utf-8');

    return res.json({
      success: true,
      message: '이슈가 성공적으로 수정되었습니다.',
      data: {
        issue,
      },
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: '이슈 수정 중 서버 오류 발생',
      error: error.message,
    });
  }
});

app.get('/issues/:id', authMiddleware, async (req, res) => {
  try {
    const issueId = parseInt(req.params.id);
    const filePath = path.join(__dirname, 'mainPage.json');
    const data = await fs.readFile(filePath, 'utf-8');
    const json = JSON.parse(data);

    const issue = json.issues.find((issue) => issue.id === issueId);

    if (!issue) {
      return res.status(404).json({
        success: false,
        message: '이슈를 찾을 수 없습니다.',
        data: null,
      });
    }

    const responseData = {
      success: true,
      message: '이슈를 성공적으로 조회했습니다.',
      data: {
        issue: {
          issueId: issue.id,
          title: issue.title,
          content: issue.content,
          authorId: issue.author.id,
          authorNickname: issue.author.nickname,
          milestoneId: issue.milestone?.milestoneId ?? null,
          isOpen: issue.isOpen,
          lastModifiedAt: issue.lastModifiedAt || issue.createdAt,
          issueFileUrl: issue.issueFileUrl || null,
          authorProfileUrl:
            issue.author.profileImageUrl ||
            `https://dummy.local/profile/${issue.author.nickname}.png`,
        },
        assignees: (issue.assignees || []).map((assignee) => ({
          id: assignee.id,
          nickname: assignee.nickname,
          profileImageUrl:
            assignee.profileImageUrl || `https://dummy.local/profile/${assignee.nickname}.png`,
        })),
        labels: (issue.labels || []).map((label) => ({
          labelId: label.labelId,
          name: label.name,
          color: label.color,
          description: label.description || '',
        })),
        milestone: issue.milestone
          ? {
              milestoneId: issue.milestone.milestoneId,
              name: issue.milestone.name,
              description: issue.milestone.description,
              endDate: issue.milestone.endDate,
              processingRate: issue.milestone.processingRate || 0,
              isOpen: issue.milestone.isOpen,
            }
          : null,
        comments: (issue.comments || []).map((comment) => {
          const user = json.users.find((u) => u.nickname === comment.authorNickname);
          return {
            commentId: comment.commentId,
            content: comment.content,
            issueFileUrl: comment.issueFileUrl || null,
            authorId: user?.id || 1,
            authorNickname: comment.authorNickname,
            lastModifiedAt: comment.lastModifiedAt || comment.createdAt,
            authorProfileUrl:
              comment.authorProfileUrl ||
              `https://dummy.local/profile/${comment.authorNickname}.png`,
          };
        }),
      },
    };

    res.json(responseData);
  } catch (error) {
    res.status(500).json({
      success: false,
      message: '이슈 조회 중 서버 오류 발생',
      error: error.message,
    });
  }
});

app.get('/milestones', authMiddleware, async (req, res) => {
  try {
    const { isOpen } = req.query;
    const filePath = path.join(__dirname, 'mainPage.json');
    const json = JSON.parse(await fs.readFile(filePath, 'utf-8'));
    let milestones = json.milestones;

    // 마일스톤 개수 카운트 (isOpen 필터링 전, 전체)
    const openCount = milestones.filter((m) => m.isOpen === true).length;
    const closedCount = milestones.filter((m) => m.isOpen === false).length;

    // isOpen 필터링 (응답의 milestones만)
    if (typeof isOpen !== 'undefined') {
      milestones = milestones.filter((m) => String(m.isOpen) === String(isOpen));
    }

    res.json({
      success: true,
      message: '마일스톤 목록 조회 성공',
      data: {
        milestones,
        openCount,
        closedCount,
      },
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: '마일스톤 목록 조회 중 서버 오류 발생',
      data: { error: error.message },
    });
  }
});

app.listen(PORT, () => {
  console.log(`🟢 Mock server running at http://localhost:${PORT}`);
});
