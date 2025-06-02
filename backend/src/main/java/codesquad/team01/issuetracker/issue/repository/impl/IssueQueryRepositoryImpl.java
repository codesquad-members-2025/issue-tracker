package codesquad.team01.issuetracker.issue.repository.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquad.team01.issuetracker.common.dto.CursorDto;
import codesquad.team01.issuetracker.issue.constants.IssueConstants;
import codesquad.team01.issuetracker.issue.domain.IssueState;
import codesquad.team01.issuetracker.issue.dto.IssueDto;
import codesquad.team01.issuetracker.issue.exception.IssueCreationException;
import codesquad.team01.issuetracker.issue.exception.IssueNotFoundException;
import codesquad.team01.issuetracker.issue.exception.IssueUpdateException;
import codesquad.team01.issuetracker.issue.repository.IssueQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class IssueQueryRepositoryImpl implements IssueQueryRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	private static final String BASE_ISSUE_QUERY = """
		SELECT
		    i.id as issue_id,
		    i.title as issue_title,
		    i.state as issue_state,
		    i.created_at as issue_created_at,
		    i.updated_at as issue_updated_at,
		    u.id as writer_id,
		    u.username as writer_username,
		    u.profile_image_url as writer_profile_image_url,
		    m.id as milestone_id,
		    m.title as milestone_title
		FROM issue i
		JOIN users u ON i.writer_id = u.id
		LEFT JOIN milestone m ON i.milestone_id = m.id
		WHERE i.deleted_at IS NULL
		    AND i.state = :state 
		""";

	private static final String BASE_COUNT_ISSUES_QUERY = """
		SELECT 
			i.state,
			COUNT(*) as count
		FROM issue i
		WHERE i.deleted_at IS NULL 
		""";

	private static final String UPDATE_ISSUE_STATES_BATCH_QUERY = """
		UPDATE issue
		SET state = :state,
			closed_at = CASE
				WHEN :state = 'CLOSED' THEN :now
				ELSE NULL
			END,
			updated_at = :now
		WHERE id IN (:issueIds)
		AND deleted_at IS NULL
		""";

	private static final String FIND_EXISTING_ISSUES_QUERY = """
		SELECT 
			i.id as issue_id,
			i.state as issue_state
		FROM issue i
		WHERE i.id IN (:issueIds)
		AND i.deleted_at IS NULL
		""";

	private static final String CREATE_ISSUE_QUERY = """
		INSERT INTO issue (title, content, state, writer_id, milestone_id, created_at, updated_at)
		VALUES (:title, :content, :state, :writerId, :milestoneId, :now, :now)
		""";

	private static final String FIND_CREATED_ISSUE_QUERY = """
		SELECT 
			i.id as issue_id,
			i.title as issue_title,
			i.content as issue_content,
			i.state as issue_state,
			i.created_at as issue_created_at,
			i.updated_at as issue_updated_at,
			i.closed_at as issue_closed_at,
			u.id as writer_id,
			u.username as writer_username,
			u.profile_image_url as writer_profile_image_url,
			m.id as milestone_id,
			m.title as milestone_title,
			m.due_date as milestone_due_date
		FROM issue i
		JOIN users u ON i.writer_id = u.id
		LEFT JOIN milestone m ON i.milestone_id = m.id
		WHERE i.id = :issueId
		AND i.deleted_at IS NULL
		""";

	private static final String INSERT_ISSUE_LABELS_BATCH_QUERY = """
		INSERT INTO issue_label (issue_id, label_id)
		VALUES (:issueId, :labelId)
		""";

	private static final String INSERT_ISSUE_ASSIGNEES_BATCH_QUERY = """
		INSERT INTO issue_assignee (issue_id, user_id)
		VALUES (:issueId, :userId)
		""";

	private static final String DELETE_ISSUE_LABELS_QUERY = """
		DELETE FROM issue_label WHERE issue_id = :issueId
		""";

	private static final String DELETE_ISSUE_ASSIGNEES_QUERY = """
		DELETE FROM issue_assignee WHERE issue_id = :issueId
		""";

	private static final String UPDATE_ISSUE_TITLE_QUERY = """
		UPDATE issue
		SET title = :title, updated_at = :now
		WHERE id = :issueId AND deleted_at IS NULL
		""";
	private static final String UPDATE_ISSUE_CONTENT_QUERY = """
				UPDATE issue
				SET content = :content, updated_at :now
				WHERE id = :issueId AND deleted_at IS NULL
		""";
	private static final String UPDATE_ISSUE_MILESTONE_QUERY = """
				UPDATE issue
				SET milestone_id = :milestoneId, updated_at = :now
				WHERE id = :issueId AND deleted_at IS NULL
		""";
	private static final String UPDATE_ISSUE_STATE_QUERY = """
				UPDATE issue
				SET state = :state,
					closed_at = CASE
						WHEN :state = 'CLOSED' THEN :now
						ELSE NULL
					END,
					updated_at = :now
				WHERE id = :issueId AND deleted_at IS NULL
		""";

	private static final String FIND_ISSUE_STATE_AND_WRITER_ID_QUERY = """
		SELECT 
			i.state,
			i.writer_id
		FROM issue i 
		WHERE i.id = :issueId 
		AND i.deleted_at IS NULL
		  """;
	private final RowMapper<IssueDto.BaseRow> issueRowMapper = (rs, rowNum) -> {

		Integer milestoneId = rs.getObject("milestone_id", Integer.class);
		String milestoneTitle = rs.getString("milestone_title");

		return IssueDto.BaseRow.builder()
			.issueId(rs.getInt("issue_id"))
			.issueTitle(rs.getString("issue_title"))
			.issueState(IssueState.fromStateStr(rs.getString("issue_state")))
			.issueCreatedAt(rs.getTimestamp("issue_created_at").toLocalDateTime())
			.issueUpdatedAt(rs.getTimestamp("issue_updated_at").toLocalDateTime())
			.writerId(rs.getInt("writer_id"))
			.writerUsername(rs.getString("writer_username"))
			.writerProfileImageUrl(rs.getString("writer_profile_image_url"))
			.milestoneId(milestoneId)
			.milestoneTitle(milestoneTitle)
			.build();
	};

	private final RowMapper<IssueDto.DetailBaseRow> detailIssueRowMapper =
		(rs, rowNum) -> {

			Timestamp closedAtTimestamp = rs.getTimestamp("issue_closed_at");
			LocalDateTime closedAt = closedAtTimestamp != null ?
				closedAtTimestamp.toLocalDateTime() : null;

			Integer milestoneId = rs.getObject("milestone_id", Integer.class);
			String milestoneTitle = rs.getString("milestone_title");
			Date milestoneDueDateSql = rs.getDate("milestone_due_date");
			LocalDate milestoneDueDate = milestoneDueDateSql != null ?
				milestoneDueDateSql.toLocalDate() : null;

			return IssueDto.DetailBaseRow.builder()
				.issueId(rs.getInt("issue_id"))
				.issueTitle(rs.getString("issue_title"))
				.issueContent(rs.getString("issue_content"))
				.issueState(IssueState.fromStateStr(rs.getString("issue_state")))
				.issueCreatedAt(rs.getTimestamp("issue_created_at").toLocalDateTime())
				.issueUpdatedAt(rs.getTimestamp("issue_updated_at").toLocalDateTime())
				.issueClosedAt(closedAt) // null-safe 처리된 값
				.writerId(rs.getInt("writer_id"))
				.writerUsername(rs.getString("writer_username"))
				.writerProfileImageUrl(rs.getString("writer_profile_image_url"))
				.milestoneId(milestoneId)
				.milestoneTitle(milestoneTitle)
				.milestoneDueDate(milestoneDueDate)
				.build();
		};

	private final RowMapper<IssueDto.StateCountRow> stateCountRowMapper = (rs, rowNum) ->
		IssueDto.StateCountRow.builder()
			.state(rs.getString("state"))
			.count(rs.getInt("count"))
			.build();

	private final RowMapper<IssueDto.BatchIssueRow> batchIssueRowMapper = (rs, rowNum) ->
		IssueDto.BatchIssueRow.builder()
			.issueId(rs.getInt("issue_id"))
			.currentState(IssueState.fromStateStr(rs.getString("issue_state")))
			.build();

	private final RowMapper<IssueDto.IssueStateAndWriterIdRow> stateAndWriterRowMapper =
		(rs, rowNum) -> IssueDto.IssueStateAndWriterIdRow.builder()
			.state(IssueState.fromStateStr(rs.getString("state")))
			.writerId(rs.getInt("writer_id"))
			.build();

	@Override
	public List<IssueDto.BaseRow> findIssuesWithFilters(
		IssueState state, Integer writerId, Integer milestoneId,
		List<Integer> labelIds, List<Integer> assigneeIds, CursorDto.CursorData cursor) {

		StringBuilder sql = new StringBuilder(BASE_ISSUE_QUERY);
		MapSqlParameterSource params = new MapSqlParameterSource();

		// state
		params.addValue("state", state.name());

		appendFilterConditions(sql, params, writerId, milestoneId, labelIds, assigneeIds);

		// 무한스크롤 커서
		if (cursor != null) {
			sql.append("""
				AND (i.created_at < :cursorCreatedAt 
				     OR (i.created_at = :cursorCreatedAt AND i.id < :cursorId))
				""");
			params.addValue("cursorCreatedAt", cursor.getCreatedAt());
			params.addValue("cursorId", cursor.getId());
		}

		// 정렬: 생성일자 내림차순 (최신순)
		sql.append(" ORDER BY i.created_at DESC, i.id DESC");

		sql.append(" LIMIT :pageSize");
		params.addValue("pageSize", IssueConstants.PAGE_SIZE + 1); // 다음 페이지 존재 여부 확인을 위해 +1

		return jdbcTemplate.query(sql.toString(), params, issueRowMapper);
	}

	@Override
	public IssueDto.CountResponse countIssuesWithFilters(Integer writerId, Integer milestoneId,
		List<Integer> labelIds, List<Integer> assigneeIds) {

		StringBuilder sql = new StringBuilder(BASE_COUNT_ISSUES_QUERY);
		MapSqlParameterSource params = new MapSqlParameterSource();

		appendFilterConditions(sql, params, writerId, milestoneId, labelIds, assigneeIds);

		sql.append(" GROUP BY i.state");

		List<IssueDto.StateCountRow> results = jdbcTemplate.query(sql.toString(), params, stateCountRowMapper);

		Map<IssueState, Integer> countByState = results.stream()
			.collect(Collectors.toMap(
				row -> IssueState.fromStateStr(row.state()),
				IssueDto.StateCountRow::count
			));

		return IssueDto.CountResponse.builder()
			.open(countByState.getOrDefault(IssueState.OPEN, 0))
			.closed(countByState.getOrDefault(IssueState.CLOSED, 0))
			.build();
	}

	@Override
	public int batchUpdateIssueStates(List<Integer> issueIds, IssueState targetState, LocalDateTime now) {
		if (issueIds.isEmpty()) {
			return 0;
		}

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("issueIds", issueIds);
		params.addValue("state", targetState.name());
		params.addValue("now", now);

		return jdbcTemplate.update(UPDATE_ISSUE_STATES_BATCH_QUERY, params);
	}

	@Override
	public List<IssueDto.BatchIssueRow> findExistingIssuesByIds(List<Integer> issueIds) {
		if (issueIds.isEmpty()) {
			return List.of();
		}
		MapSqlParameterSource params = new MapSqlParameterSource("issueIds", issueIds);
		return jdbcTemplate.query(FIND_EXISTING_ISSUES_QUERY, params, batchIssueRowMapper);
	}

	@Override
	public Integer createIssue(String title, String content, Integer writerId, Integer milestoneId, LocalDateTime now) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("title", title);
		params.addValue("content", content);
		params.addValue("state", IssueState.OPEN.name());
		params.addValue("writerId", writerId);
		params.addValue("milestoneId", milestoneId);
		params.addValue("now", now);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		try {
			int rowsAffected = jdbcTemplate.update(CREATE_ISSUE_QUERY, params, keyHolder);

			if (rowsAffected != 1) {
				throw new IssueCreationException("이슈 생성 실패. 영향 받은 행: " + rowsAffected);
			}

			Number generatedId = keyHolder.getKey();
			if (generatedId == null) {
				throw new IssueCreationException("생성된 이슈 ID를 가져올 수 없습니다.");
			}

			Integer issueId = generatedId.intValue();
			log.info("ID: {} 이슈 생성 완료: 제목={}", issueId, title);

			return issueId;
		} catch (DataAccessException e) {
			log.error("이슈 생성 중 db 오류 발생: {}", e.getMessage(), e);
			throw new IssueCreationException("데이터베이스 오류로 인해 이슈 생성 실패", e);
		}
	}

	@Override
	public IssueDto.DetailBaseRow findCreatedIssueById(Integer issueId) {
		MapSqlParameterSource params = new MapSqlParameterSource("issueId", issueId);

		try {
			List<IssueDto.DetailBaseRow> results =
				jdbcTemplate.query(FIND_CREATED_ISSUE_QUERY, params, detailIssueRowMapper);

			if (results.isEmpty()) {
				throw new IssueNotFoundException("생성된 이슈를 찾을 수 없습니다: " + issueId);
			}

			return results.get(0);
		} catch (DataAccessException e) {
			log.error("이슈 조회 중 데이터베이스 오류 발생: issueId={}, error={}", issueId, e.getMessage());
			throw new IssueNotFoundException("이슈 조회 중 오류 발생: " + issueId, e);
		}
	}

	@Override
	public void addLabelsToIssue(Integer issueId, List<Integer> labelIds) {
		if (labelIds.isEmpty()) {
			return;
		}

		Set<Integer> uniqueLabelIds = new LinkedHashSet<>(labelIds);

		List<MapSqlParameterSource> batchParams = uniqueLabelIds.stream()
			.map(labelId -> {
				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("issueId", issueId);
				params.addValue("labelId", labelId);
				return params;
			})
			.toList();

		try {
			int[] results = jdbcTemplate.batchUpdate(
				INSERT_ISSUE_LABELS_BATCH_QUERY,
				batchParams.toArray(new MapSqlParameterSource[0])
			);

			int successCount = (int)Arrays.stream(results).filter(result -> result > 0).count();
			log.debug("이슈 {}에 {}개 레이블 추가 완료 (성공: {}개)", issueId, labelIds.spliterator().estimateSize(), successCount);
		} catch (DataAccessException e) {
			log.error("이슈 레이블 추가 중 오류 발생: issueId={}, labelIds={}, error={}",
				issueId, labelIds, e.getMessage());
			throw new IssueCreationException("이슈 레이블 추가 중 오류 발생", e);
		}
	}

	@Override
	public void addAssigneesToIssue(Integer issueId, List<Integer> assigneeIds) {
		if (assigneeIds.isEmpty()) {
			return;
		}

		Set<Integer> uniqueAssigneeIds = new LinkedHashSet<>(assigneeIds);

		List<MapSqlParameterSource> batchParams = uniqueAssigneeIds.stream()
			.map(userId -> {
				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("issueId", issueId);
				params.addValue("userId", userId);
				return params;
			})
			.toList();

		try {
			int[] results = jdbcTemplate.batchUpdate(
				INSERT_ISSUE_ASSIGNEES_BATCH_QUERY,
				batchParams.toArray(new MapSqlParameterSource[0])
			);

			int successCount = (int)Arrays.stream(results).filter(result -> result > 0).count();
			log.debug("이슈 {}에 {}개 담당자 추가 완료 (성공: {}개)", issueId, assigneeIds.size(), successCount);

		} catch (DataAccessException e) {
			log.error("이슈 담당자 추가 중 오류 발생: issueId={}, assigneeIds={}, error={}",
				issueId, assigneeIds, e.getMessage());
			throw new IssueCreationException("이슈 담당자 추가 중 오류 발생", e);
		}
	}

	@Override
	public void removeLabelsFromIssue(Integer issueId) {
		MapSqlParameterSource params = new MapSqlParameterSource("issueId", issueId);

		try {
			int deletedCount = jdbcTemplate.update(DELETE_ISSUE_LABELS_QUERY, params);
			log.debug("이슈 {}의 모든 레이블 제거 완료: {}개", issueId, deletedCount);
		} catch (DataAccessException e) {
			log.error("이슈 레이블 제거 중 오류 발생: issueId={}, error={}", issueId, e.getMessage());
			throw new IssueCreationException("이슈 레이블 제거 중 오류 발생", e);
		}
	}

	@Override
	public void removeAssigneesFromIssue(Integer issueId) {
		MapSqlParameterSource params = new MapSqlParameterSource("issueId", issueId);

		try {
			int deletedCount = jdbcTemplate.update(DELETE_ISSUE_ASSIGNEES_QUERY, params);
			log.debug("이슈 {}의 모든 담당자 제거 완료: {}개", issueId, deletedCount);
		} catch (DataAccessException e) {
			log.error("이슈 담당자 제거 중 오류 발생: issueId={}, error={}", issueId, e.getMessage());
			throw new IssueCreationException("이슈 담당자 제거 중 오류 발생", e);
		}
	}

	@Override
	public void updateIssueTitle(Integer issueId, String title, LocalDateTime now) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("issueId", issueId);
		params.addValue("title", title);
		params.addValue("now", now);

		int updatedRows = jdbcTemplate.update(UPDATE_ISSUE_TITLE_QUERY, params);
		if (updatedRows == 0) {
			throw new IssueNotFoundException("조회할 수 없거나 삭제된 이슈: " + issueId);
		}
	}

	@Override
	public void updateIssueContent(Integer issueId, String content, LocalDateTime now) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("issueId", issueId);
		params.addValue("content", content);
		params.addValue("now", now);

		int updatedRows = jdbcTemplate.update(UPDATE_ISSUE_CONTENT_QUERY, params);
		if (updatedRows == 0) {
			throw new IssueNotFoundException("조회할 수 없거나 삭제된 이슈: " + issueId);
		}
	}

	@Override
	public void updateIssueMilestone(Integer issueId, Integer milestoneId, LocalDateTime now) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("issueId", issueId);
		params.addValue("milestoneId", milestoneId);
		params.addValue("now", now);

		int updatedRows = jdbcTemplate.update(UPDATE_ISSUE_MILESTONE_QUERY, params);
		if (updatedRows == 0) {
			throw new IssueNotFoundException("조회할 수 없거나 삭제된 이슈: " + issueId);
		}
	}

	@Override
	public void updateIssueState(Integer issueId, IssueState targetState, LocalDateTime now) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("issueId", issueId);
		params.addValue("state", targetState.name());
		params.addValue("now", now);

		int updatedRows = jdbcTemplate.update(UPDATE_ISSUE_STATE_QUERY, params);
		if (updatedRows == 0) {
			throw new IssueNotFoundException("조회할 수 없거나 삭제된 이슈: " + issueId);
		}
	}

	@Override
	public IssueDto.IssueStateAndWriterIdRow findIssueStateAndWriterIdByIssueId(Integer issueId) {
		MapSqlParameterSource params = new MapSqlParameterSource("issueId", issueId);

		try {
			return jdbcTemplate.queryForObject(FIND_ISSUE_STATE_AND_WRITER_ID_QUERY, params, stateAndWriterRowMapper);

		} catch (EmptyResultDataAccessException e) {
			throw new IssueNotFoundException("존재하지 않는 이슈입니다: " + issueId);
		} catch (DataAccessException e) {
			log.error("이슈 상태 및 작성자 조회 중 데이터베이스 오류 발생: issueId={}, error={}", issueId, e.getMessage());
			throw new IssueUpdateException("이슈 정보 조회 중 오류가 발생했습니다.", e);
		}
	}

	private void appendFilterConditions(StringBuilder sql, MapSqlParameterSource params,
		Integer writerId, Integer milestoneId,
		List<Integer> labelIds, List<Integer> assigneeIds) {

		// 작성자
		if (writerId != null) {
			sql.append(" AND i.writer_id = :writerId");
			params.addValue("writerId", writerId);
		}

		// 마일스톤
		if (milestoneId != null) {
			sql.append(" AND i.milestone_id = :milestoneId");
			params.addValue("milestoneId", milestoneId);
		}

		// 레이블
		if (labelIds != null && !labelIds.isEmpty()) {
			for (int i = 0; i < labelIds.size(); i++) {
				sql.append("""
					AND EXISTS (
					   SELECT 1 FROM issue_label il2 
					   WHERE il2.issue_id = i.id 
					   AND il2.label_id = :labelId""").append(i).append(")\n");
				params.addValue("labelId" + i, labelIds.get(i));
			}
		}

		// 담당자
		if (assigneeIds != null && !assigneeIds.isEmpty()) {
			for (int i = 0; i < assigneeIds.size(); i++) {
				sql.append("""
					AND EXISTS (
					   SELECT 1 FROM issue_assignee ia2 
					   WHERE ia2.issue_id = i.id 
					   AND ia2.user_id = :assigneeId""").append(i).append(")\n");
				params.addValue("assigneeId" + i, assigneeIds.get(i));
			}
		}
	}
	
}
