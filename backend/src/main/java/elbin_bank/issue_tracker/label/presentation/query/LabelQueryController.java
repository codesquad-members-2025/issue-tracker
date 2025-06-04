package elbin_bank.issue_tracker.label.presentation.query;

import elbin_bank.issue_tracker.label.application.query.LabelQueryService;
import elbin_bank.issue_tracker.label.application.query.dto.LabelsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/labels")
@RequiredArgsConstructor
public class LabelQueryController {

    private final LabelQueryService labelQueryService;

    @GetMapping("")
    public ResponseEntity<LabelsResponseDto> getLabels() {
        return ResponseEntity.ok(labelQueryService.findAll());
    }

}
