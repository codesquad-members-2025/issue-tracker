package codesquad.team01.issuetracker.common.dto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import codesquad.team01.issuetracker.common.exception.CursorException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CursorDto {

	private CursorDto() {
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
					.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
					.writeValueAsString(this);

				return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
			} catch (JsonProcessingException e) { // 인코딩 실패 시
				throw new CursorException("json 직렬화 실패");
			} catch (Exception e) {
				throw new CursorException(e.getMessage());
			}
		}
	}

	@Builder
	public record CursorResponse(
		String next,
		boolean hasNext
	) {
	}
}
