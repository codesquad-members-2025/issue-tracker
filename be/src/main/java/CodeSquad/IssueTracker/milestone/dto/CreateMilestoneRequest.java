package CodeSquad.IssueTracker.milestone.dto;

import CodeSquad.IssueTracker.milestone.Milestone;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateMilestoneRequest {
    @NotBlank(message = "마일스톤의 이름은 필수로 지정해야합니다.")
    private String name;
    @NotBlank(message = "마일스톤의 설명을 필수적으로 넣어주세요")
    private String description;
    private LocalDateTime endDate;

    public Milestone toEntity(){
        return new Milestone(
                name,description,endDate,true,LocalDateTime.now()
        );
    }
}
