package elbin_bank.issue_tracker.milestone.presentation.query;

import elbin_bank.issue_tracker.milestone.application.query.MilestoneQueryService;
import elbin_bank.issue_tracker.milestone.application.query.dto.MilestoneShortsResponseDto;
import elbin_bank.issue_tracker.milestone.application.query.dto.MilestonesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/milestones")
@RequiredArgsConstructor
public class MilestoneQueryController {

    private final MilestoneQueryService milestoneQueryService;

    @GetMapping("")
    public ResponseEntity<MilestonesResponseDto> getMilestonesForMain() {
        return ResponseEntity.ok(milestoneQueryService.getMilestones());
    }

    @GetMapping("/short")
    public ResponseEntity<MilestoneShortsResponseDto> getMilestonesForSelectBox() {
        return ResponseEntity.ok(milestoneQueryService.getMilestonesForSelectBox());
    }

}
