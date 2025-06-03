package elbin_bank.issue_tracker.milestone.presentation.command;

import elbin_bank.issue_tracker.milestone.application.command.MilestoneCommandService;
import elbin_bank.issue_tracker.milestone.presentation.command.dto.request.MilestoneCreateRequestDto;
import elbin_bank.issue_tracker.milestone.presentation.command.dto.request.MilestoneUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/milestones")
public class MilestoneCommandController {

    private final MilestoneCommandService milestoneCommandService;

    @PostMapping("")
    public ResponseEntity<Void> createMilestone(@RequestBody MilestoneCreateRequestDto milestoneCreateRequestDto) {
        milestoneCommandService.createMilestone(milestoneCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> updateMilestone(@RequestBody MilestoneUpdateRequestDto milestoneUpdateRequestDto, @PathVariable Long id) {
        milestoneCommandService.updateMilestone(milestoneUpdateRequestDto, id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable Long id) {
        milestoneCommandService.deleteMilestone(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
