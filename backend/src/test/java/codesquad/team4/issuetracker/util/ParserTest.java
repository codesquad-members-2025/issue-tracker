package codesquad.team4.issuetracker.util;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ParserTest {
    @ParameterizedTest
    @MethodSource("provideFilterConditions")
    @DisplayName("문자열 필터링 조건을 파싱하여 Dto로 만든다")
    void filteringConditionToDto(String q, String state, Long authorId, Long assigneeId, Long milestoneId, Long commentAuthorId, List<Long> labelIds) {
        // when
        IssueRequestDto.IssueFilterParamDto filterDto = Parser.parseFilterCondition(q);

        // then
        assertThat(filterDto.getStatus().getValue()).isEqualTo(state);
        assertThat(filterDto.getAuthorId()).isEqualTo(authorId);
        assertThat(filterDto.getAssigneeId()).isEqualTo(assigneeId);
        assertThat(filterDto.getMilestoneId()).isEqualTo(milestoneId);
        assertThat(filterDto.getCommentAuthorId()).isEqualTo(commentAuthorId);
        assertThat(filterDto.getLabelIds()).containsExactlyElementsOf(labelIds);
    }

    private static Stream<Arguments> provideFilterConditions() {
        return Stream.of(
            Arguments.of("q=state:open&page=1&size=10", "open", null, null, null, null, List.of()),
            Arguments.of("q=state:close&page=1&size=10", "close", null, null, null, null, List.of()),
            Arguments.of("q=state:open+authorId:3&page=1&size=10", "open", 3L, null, null, null, List.of()),
            Arguments.of("q=state:open+assigneeId:2&page=1&size=10", "open", null, 2L, null, null, List.of()),
            Arguments.of("q=state:open+commentAuthorId:3&page=1&size=10", "open", null, null, null, 3L, List.of()),
            Arguments.of("q=state:open+authorId:3+labelId:1&page=1&size=10", "open", 3L, null, null, null, List.of(1L)),
            Arguments.of("q=authorId:3+labelId:1+labelId:2&page=1&size=10", "open", 3L, null, null, null, List.of(1L, 2L)),
            Arguments.of("q=state:open+authorId:3+milestoneId:1&page=1&size=10", "open", 3L, null, 1L, null, List.of()),
            Arguments.of("q=state:open+labelId:1+labelId:2+labelId:3&page=1&size=10", "open", null, null, null, null, List.of(1L, 2L, 3L)),
            // authorId와 assigneeId 동시 제공
            Arguments.of("q=state:open+authorId:3+assigneeId:4&page=1&size=10", "open", 3L, 4L, null, null, List.of()),
            // milestoneId만 있는 경우
            Arguments.of("q=milestoneId:2&page=1&size=10", "open", null, null, 2L, null, List.of()),
            // labelId만 있는 경우
            Arguments.of("q=labelId:5&page=1&size=10", "open", null, null, null, null, List.of(5L)),
            // state 없이 commentAuthorId만
            Arguments.of("q=commentAuthorId:2&page=1&size=10", "open", null, null, null, 2L, List.of()),
            // 필터링이 아무것도 없는 경우
            Arguments.of("q=&page=1&size=10", "open", null, null, null, null, List.of()),
            // 필드 순서가 바뀐 경우
            Arguments.of("q=authorId:3+state:open&page=1&size=10", "open", 3L, null, null, null, List.of()),
            // labelId 중복 테스트
            Arguments.of("q=labelId:1+labelId:1&page=1&size=10", "open", null, null, null, null, List.of(1L, 1L)),
            // state가 대소문자 섞인 경우
            Arguments.of("q=state:Open&page=1&size=10", "open", null, null, null, null, List.of())
        );
    }
}
