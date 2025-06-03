package CodeSquad.IssueTracker.label;

import CodeSquad.IssueTracker.label.dto.CreateLabelRequest;
import CodeSquad.IssueTracker.label.dto.LabelListResponse;
import CodeSquad.IssueTracker.label.dto.LabelResponse;
import CodeSquad.IssueTracker.label.dto.LabelUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/labels")
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;

    @GetMapping
    public LabelListResponse getLabels() {
        List<Label> labels = labelService.findAll();

        List<LabelResponse> responseList = labels.stream()
                .map(LabelResponseConverter::toLabelResponse)
                .toList();

        return new LabelListResponse(responseList);
    }

    @PostMapping
    public String createLabel(@RequestBody CreateLabelRequest request){
        labelService.save(request.toEntity());
        return "redirect:/milestones";
    }

    @PatchMapping(value = "/{labelId}")
    public String updateLabel(@RequestBody LabelUpdateDto request,@PathVariable Long labelId){
        labelService.update(labelId,request);
        return "redirect:/milestones";
    }


}
