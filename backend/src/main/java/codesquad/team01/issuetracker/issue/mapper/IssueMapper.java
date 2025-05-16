package codesquad.team01.issuetracker.issue.mapper;

import codesquad.team01.issuetracker.issue.dto.IssueJoinRow;
import codesquad.team01.issuetracker.issue.dto.IssueSimpleResponse;
import codesquad.team01.issuetracker.label.dto.LabelSimpleResponse;
import codesquad.team01.issuetracker.milestone.dto.MilestoneSimpleResponse;
import codesquad.team01.issuetracker.user.dto.UserResponse;
import codesquad.team01.issuetracker.user.dto.UserSimpleResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IssueMapper {

    @Mapping(source = "issueId", target = "id")
    @Mapping(source = "issueTitle", target = "title")
    @Mapping(source = "issueOpen", target = "isOpen")
    @Mapping(source = "issueCreatedAt", target = "createdAt")
    @Mapping(source = "issueUpdatedAt", target = "updatedAt")
    @Mapping(target = "writer", expression = "java(toWriter(row))")
    @Mapping(target = "milestone", expression = "java(row.milestoneId() != null ? toMilestone(row) : null)")
    @Mapping(target = "assignees", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "labels", expression = "java(new java.util.ArrayList<>())")
    IssueSimpleResponse toIssueResponse(IssueJoinRow row);

    @Mapping(source = "writerId", target = "id")
    @Mapping(source = "writerUsername", target = "username")
    @Mapping(source = "writerProfileImageUrl", target = "profileImageUrl")
    UserResponse toWriter(IssueJoinRow row);

    @Mapping(source = "assigneeId", target = "id")
    @Mapping(source = "assigneeProfileImageUrl", target = "profileImageUrl")
    UserSimpleResponse toAssignee(IssueJoinRow row);

    @Mapping(source = "milestoneId", target = "id")
    @Mapping(source = "milestoneTitle", target = "title")
    MilestoneSimpleResponse toMilestone(IssueJoinRow row);

    @Mapping(source = "labelId", target = "id")
    @Mapping(source = "labelName", target = "name")
    @Mapping(source = "labelColor", target = "color")
    @Mapping(source = "labelTextColor", target = "textColor")
    LabelSimpleResponse toLabel(IssueJoinRow row);
}
