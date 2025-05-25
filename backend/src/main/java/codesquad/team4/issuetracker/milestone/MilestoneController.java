package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.milestone.dto.MilestoneRequestDto;
import codesquad.team4.issuetracker.milestone.dto.MilestoneResponseDto;
import codesquad.team4.issuetracker.milestone.dto.MilestoneResponseDto.MilestoneFilter;
import codesquad.team4.issuetracker.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMilestone(@RequestBody @Valid MilestoneRequestDto.CreateMilestoneDto request) {
        milestoneService.createMilestone(request);
    }

    @PutMapping("/{milestone-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMilestone(
        @PathVariable("milestone-id") Long milestoneId,
        @RequestBody @Valid MilestoneRequestDto.CreateMilestoneDto request) {
        milestoneService.updateMilestone(milestoneId, request);
    }

    @DeleteMapping("/{milestone-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMilestone(@PathVariable("milestone-id") Long milestoneId) {
        milestoneService.deleteMilestone(milestoneId);
    }

    @PatchMapping("/{milestone-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeMilestone(@PathVariable("milestone-id") Long milestoneId) {
        milestoneService.closeMilestone(milestoneId);
    }
}
