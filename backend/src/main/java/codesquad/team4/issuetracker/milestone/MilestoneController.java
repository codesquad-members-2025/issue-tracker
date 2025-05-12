package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.milestone.dto.MilestoneCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/milestones")
public class MilestoneController {
    private final MilestoneService milestoneService;

    @GetMapping("/count")
    public ResponseEntity<MilestoneCountDto> getMilestoneCount() {
        MilestoneCountDto result = milestoneService.getMilestoneCount();

        return ResponseEntity.ok(result);
    }
}
