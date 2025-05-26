package CodeSquad.IssueTracker.milestone;

import CodeSquad.IssueTracker.milestone.dto.CreateMilestoneRequest;
import CodeSquad.IssueTracker.milestone.dto.MilestoneUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/milestones")
@RequiredArgsConstructor
public class MilestoneController {

    private final MilestoneService milestoneService;

    @PostMapping
    public String createMilestone(@RequestBody CreateMilestoneRequest request) {
        milestoneService.save(request.toEntity());
        return "redirect:/milestones";
    }


    @PatchMapping("/{milestoneId}")
    public String updateMilestone(@RequestParam Long milestoneId, @RequestBody MilestoneUpdateDto request){
        milestoneService.update(milestoneId,request);
        return "redirect:/milestones";
    }
}
