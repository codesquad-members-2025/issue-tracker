package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.label.dto.LabelDto;
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

    @GetMapping("/filter")
    public ResponseEntity<LabelDto.LabelFilter> getFilterLabels() {
        LabelDto.LabelFilter result = labelService.getFilterLabels();
        return ResponseEntity.ok(result);
    }

}
