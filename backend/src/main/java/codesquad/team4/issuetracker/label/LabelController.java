package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.label.dto.LabelRequestDto;
import codesquad.team4.issuetracker.label.dto.LabelResponseDto;
import codesquad.team4.issuetracker.label.dto.LabelResponseDto.LabelFilter;
import codesquad.team4.issuetracker.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/labels")
public class LabelController {
    private final LabelService labelService;

    @GetMapping("/filter")
    public ApiResponse<LabelFilter> getFilterLabels() {
        LabelResponseDto.LabelFilter result = labelService.getFilterLabels();
        return ApiResponse.success(result);
    }

    @GetMapping("")
    public ApiResponse<LabelResponseDto.LabelListDto> showLableList() {
         LabelResponseDto.LabelListDto labels = labelService.getAllLabels();
         return ApiResponse.success(labels);
    }

    @PostMapping( "")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLabel(@RequestBody @Valid LabelRequestDto.CreateLabelDto request) {
        labelService.createLabel(request);
    }

    @PutMapping("/{label-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(
        @PathVariable("label-id") Long labelId,
        @RequestBody @Valid LabelRequestDto.CreateLabelDto request) {
        labelService.updateLabel(labelId, request);
    }

    @DeleteMapping("/{label-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable("label-id") Long labelId) {
        labelService.deleteLabel(labelId);
    }
}
