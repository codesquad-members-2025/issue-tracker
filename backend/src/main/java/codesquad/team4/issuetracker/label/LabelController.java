package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.label.dto.LabelCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/labels")
public class LabelController {
    private final LabelService labelService;

    @GetMapping("/count")
    public ResponseEntity<LabelCountDto> getLabelCount() {
        LabelCountDto result = labelService.getLabelCount();
        return ResponseEntity.ok(result);
    }

}
