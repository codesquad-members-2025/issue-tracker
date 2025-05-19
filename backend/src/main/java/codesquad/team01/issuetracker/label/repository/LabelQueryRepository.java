package codesquad.team01.issuetracker.label.repository;

import codesquad.team01.issuetracker.label.dto.LabelDto;

import java.util.List;

public interface LabelQueryRepository {
    List<LabelDto.IssueLabelRow> findLabelsByIssueIds(List<Long> issueIds);

}
