package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.milestone.dto.MilestoneDto;
import codesquad.team4.issuetracker.milestone.dto.MilestoneDto.MilestoneFilter;
import codesquad.team4.issuetracker.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/milestones")
public class MilestoneController {
    private final MilestoneService milestoneService;

    @GetMapping("/filter")
    public ApiResponse<MilestoneFilter> getFilterMilestones() {
        MilestoneDto.MilestoneFilter result = milestoneService.getFilterMilestones();
        return ApiResponse.success(result);
    }

}
