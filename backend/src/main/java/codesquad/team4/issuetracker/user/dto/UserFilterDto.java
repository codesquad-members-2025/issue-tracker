package codesquad.team4.issuetracker.user.dto;

import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserFilterDto {
    private List<IssueResponseDto.UserInfo> users;
}
