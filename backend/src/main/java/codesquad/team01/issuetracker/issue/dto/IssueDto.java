package codesquad.team01.issuetracker.issue.dto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import codesquad.team01.issuetracker.common.exception.CursorException;
import codesquad.team01.issuetracker.issue.domain.IssueState;
import codesquad.team01.issuetracker.label.dto.LabelDto;
import codesquad.team01.issuetracker.milestone.dto.MilestoneDto;
import codesquad.team01.issuetracker.user.dto.UserDto;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class IssueDto {

	private IssueDto() {
	}

	/**
	 * 요청 DTO
	 */
	// 조회 필터 쿼리 요청 DTO

	@Builder
	public record QueryRequest(
		@Pattern(regexp = "^(open|closed)$", message = "state는 'open' 또는 'closed'만 가능합니다")
		String state,

		@Positive(message = "작성자 ID는 양수여야 합니다")
		Integer writerId,

		@Positive(message = "마일스톤 ID는 양수여야 합니다")
		Integer milestoneId,

		List<@Positive(message = "레이블 ID는 양수여야 합니다") Integer> labelIds,

		List<@Positive(message = "담당자 ID는 양수여야 합니다") Integer> assigneeIds,

		String cursor // 무한스크롤 커서
	) {
		public IssueState getIssueState() {
			return IssueState.fromStateStr(state);
		}

		public String getState() { // 값이 들어오지 않은 경우 초기값 설정
			return state != null ? state : "open";
		}

		public CursorData decode() {
			if (cursor == null || cursor.isBlank()) {
				return null;
			}

			try {
				String decoded = new String(Base64.getDecoder().decode(cursor), StandardCharsets.UTF_8);
				return new ObjectMapper().readValue(decoded, CursorData.class);
			} catch (Exception e) {
				// 디코딩 실패 시 null 반환 -> 첫 페이지
				return null;
			}
		}
	}

	/**
	 * 응답 DTO
	 */
	// 이슈 목록 응답 DTO
	@Builder
	public record ListResponse(
		int totalCount,
		List<ListItemResponse> issues,
		CursorResponse cursor
	) {
	}

	// 이슈 목록 중 하나의 이슈 응답 DTO
	@Getter
	@Builder
	public static class ListItemResponse {
		private final Integer id;

		private final String title;
		private final String state;
		private final LocalDateTime createdAt;
		private final LocalDateTime updatedAt;
		private final UserDto.WriterResponse writer;
		private final MilestoneDto.ListItemResponse milestone;

		@Builder.Default
		private final List<UserDto.AssigneeResponse> assignees = new ArrayList<>();

		@Builder.Default
		private final List<LabelDto.ListItemResponse> labels = new ArrayList<>();
	}

	@Builder
	public record CursorResponse(
		String next,
		boolean hasNext
	) {
	}

	/**
	 * DB 조회용 DTO
	 */
	// 이슈 기본 정보 행 DTO
	@Builder
	public record BaseRow(
		// issue
		Integer issueId,

		String issueTitle,
		IssueState issueState,
		LocalDateTime issueCreatedAt,
		LocalDateTime issueUpdatedAt,

		// writerId
		Integer writerId,

		String writerUsername,
		String writerProfileImageUrl,

		// milestoneId - nullable
		Integer milestoneId,

		String milestoneTitle
	) {
	}

	/**
	 * 서비스 계층 DTO
	 */
	// 이슈 상세 정보 DTO
	@Builder
	public record Details(
		BaseRow baseInfo,
		List<UserDto.AssigneeResponse> assignees,
		List<LabelDto.ListItemResponse> labels
	) {
		public ListItemResponse toListItemResponse() { // Mapper 클래스로 따로 뺄지 고민
			return IssueDto.ListItemResponse.builder()
				.id(baseInfo.issueId())
				.title(baseInfo.issueTitle())
				.state(baseInfo.issueState().getValue())
				.createdAt(baseInfo.issueCreatedAt())
				.updatedAt(baseInfo.issueUpdatedAt())
				.writer(UserDto.WriterResponse.builder()
					.id(baseInfo.writerId())
					.username(baseInfo.writerUsername())
					.profileImageUrl(baseInfo.writerProfileImageUrl())
					.build()
				)
				.milestone(baseInfo.milestoneId() != null
					? MilestoneDto.ListItemResponse.builder()
					.id(baseInfo.milestoneId())
					.title(baseInfo.milestoneTitle())
					.build()
					: null)
				.assignees(assignees)
				.labels(labels)
				.build();
		}
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CursorData {
		private Integer id;
		private LocalDateTime createdAt;

		public String encode() {
			try {
				String json = new ObjectMapper()
					.registerModule(new JavaTimeModule())
					.writeValueAsString(this);
				return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
			} catch (JsonProcessingException e) { // 인코딩 실패 시
				throw new CursorException("json 직렬화 실패");
			} catch (Exception e) {
				throw new CursorException(e.getMessage());
			}
		}
	}

}


