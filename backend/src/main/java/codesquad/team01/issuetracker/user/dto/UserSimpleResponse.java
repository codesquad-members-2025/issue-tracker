package codesquad.team01.issuetracker.user.dto;

import lombok.Builder;

@Builder
public record UserSimpleResponse(
        Long id,
        String profileImageUrl
) {
}
