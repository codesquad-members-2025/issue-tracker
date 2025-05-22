package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.label.dto.LabelDto;
import codesquad.team4.issuetracker.label.dto.LabelDto.LabelFilter;
import codesquad.team4.issuetracker.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/labels")
public class LabelController {
    private final LabelService labelService;

    @GetMapping("/filter")
    public ApiResponse<LabelFilter> getFilterLabels() {
        LabelDto.LabelFilter result = labelService.getFilterLabels();
        return ApiResponse.success(result);
    }

}
