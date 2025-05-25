package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.milestone.dto.MilestoneResponseDto;
import codesquad.team4.issuetracker.milestone.dto.MilestoneResponseDto.MilestoneFilter;
import codesquad.team4.issuetracker.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/milestones")
public class MilestoneController {
    private final MilestoneService milestoneService;

    @GetMapping("/filter")
    public ApiResponse<MilestoneFilter> getFilterMilestones() {
        MilestoneResponseDto.MilestoneFilter result = milestoneService.getFilterMilestones();
        return ApiResponse.success(result);
    }

    @GetMapping("/count")
    public ApiResponse<MilestoneResponseDto.MilestoneCountDto> showMilestoneCount() {
        MilestoneResponseDto.MilestoneCountDto result = milestoneService.getMilestoneCount();
        return ApiResponse.success(result);
    }

    @GetMapping("")
    public ApiResponse<MilestoneResponseDto.MilestoneListDto> getMilestones(@RequestParam boolean isOpen) {
        MilestoneResponseDto.MilestoneListDto result = milestoneService.getMilestones(isOpen);
        return ApiResponse.success(result);
    }
}
