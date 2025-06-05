package codesquad.team4.issuetracker.util;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class IssueFilteringParserTest {
    @ParameterizedTest
    @MethodSource("provideFilterConditions")
    @DisplayName("문자열 필터링 조건을 파싱하여 Dto로 만든다")
    void filteringConditionToDto(String q, String state, Long authorId, Long assigneeId, Long milestoneId, Long commentAuthorId, List<Long> labelIds) {
        // when
        IssueRequestDto.IssueFilterParamDto filterDto = IssueFilteringParser.parseFilterCondition(q);

        // then
        assertThat(filterDto.getStatus().getValue()).isEqualTo(state);
        assertThat(filterDto.getAuthorId()).isEqualTo(authorId);
        assertThat(filterDto.getAssigneeId()).isEqualTo(assigneeId);
        assertThat(filterDto.getMilestoneId()).isEqualTo(milestoneId);
        assertThat(filterDto.getCommentAuthorId()).isEqualTo(commentAuthorId);
        assertThat(filterDto.getLabelIds()).containsAnyElementsOf(labelIds);
    }

    private static Stream<Arguments> provideFilterConditions() {
        return Stream.of(
            Arguments.of("state:open", "open", null, null, null, null, List.of()),
            Arguments.of("state:close", "close", null, null, null, null, List.of()),
            Arguments.of("state:open authorId:3", "open", 3L, null, null, null, List.of()),
            Arguments.of("state:open assigneeId:2", "open", null, 2L, null, null, List.of()),
            Arguments.of("state:open commentAuthorId:3", "open", null, null, null, 3L, List.of()),
            Arguments.of("state:open authorId:3 labelId:1", "open", 3L, null, null, null, List.of(1L)),
            Arguments.of("authorId:3 labelId:1 labelId:2", "open", 3L, null, null, null, List.of(1L, 2L)),
            Arguments.of("state:open authorId:3 milestoneId:1", "open", 3L, null, 1L, null, List.of()),
            Arguments.of("state:open labelId:1 labelId:2 labelId:3", "open", null, null, null, null, List.of(1L, 2L, 3L)),
            // authorId와 assigneeId 동시 제공
            Arguments.of("state:open authorId:3 assigneeId:4", "open", 3L, 4L, null, null, List.of()),
            // milestoneId만 있는 경우
            Arguments.of("milestoneId:2", "open", null, null, 2L, null, List.of()),
            // labelId만 있는 경우
            Arguments.of("labelId:5", "open", null, null, null, null, List.of(5L)),
            // state 없이 commentAuthorId만
            Arguments.of("commentAuthorId:2", "open", null, null, null, 2L, List.of()),
            // 필터링이 아무것도 없는 경우
            Arguments.of("", "open", null, null, null, null, List.of()),
            // 필드 순서가 바뀐 경우
            Arguments.of("authorId:3 state:open", "open", 3L, null, null, null, List.of()),
            // labelId 중복 테스트
            Arguments.of("labelId:1 labelId:1", "open", null, null, null, null, List.of(1L, 1L)),
            // state가 대소문자 섞인 경우
            Arguments.of("state:Open", "open", null, null, null, null, List.of())
        );
    }
}
