package CodeSquad.IssueTracker.milestone;

import CodeSquad.IssueTracker.global.dto.BaseResponseDto;
import CodeSquad.IssueTracker.milestone.dto.CreateMilestoneRequest;
import CodeSquad.IssueTracker.milestone.dto.MilestoneListResponse;
import CodeSquad.IssueTracker.milestone.dto.MilestoneUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static CodeSquad.IssueTracker.global.message.SuccessMessage.MILESTONE_DELETE_SUCCESS;

@RestController
@Slf4j
@RequestMapping("/milestones")
@RequiredArgsConstructor
public class MilestoneController {

    private final MilestoneService milestoneService;

    @GetMapping
    public MilestoneListResponse getMilestones(@RequestParam(defaultValue = "true") Boolean isOpen) {
       return milestoneService.getMilestonesByStatus(isOpen);
    }


    @PostMapping
    public String createMilestone(@RequestBody CreateMilestoneRequest request) {
        milestoneService.save(request.toEntity());
        return "redirect:/milestones";
    }


    @PatchMapping("/{milestoneId}")
    public String updateMilestone(@PathVariable Long milestoneId, @RequestBody MilestoneUpdateDto request){
        milestoneService.update(milestoneId,request);
        return "redirect:/milestones";
    }

    @DeleteMapping("/{milestoneId}")
    public BaseResponseDto<String> deleteMileStone(@PathVariable Long milestoneId) {
        milestoneService.deleteById(milestoneId);
        return BaseResponseDto.success(MILESTONE_DELETE_SUCCESS.getMessage(), null);
    }
}
