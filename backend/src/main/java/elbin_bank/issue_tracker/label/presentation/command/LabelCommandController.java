package elbin_bank.issue_tracker.label.presentation.command;

import elbin_bank.issue_tracker.label.application.command.LabelCommandService;
import elbin_bank.issue_tracker.label.presentation.command.dto.request.LabelCreateRequestDto;
import elbin_bank.issue_tracker.label.presentation.command.dto.request.LabelUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/labels")
public class LabelCommandController {

    private final LabelCommandService labelCommandService;

    @PostMapping("")
    public ResponseEntity<Void> createLabel(@RequestBody LabelCreateRequestDto labelCreateRequestDto) {
        labelCommandService.createLabel(labelCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateLabel(@RequestBody LabelUpdateRequestDto labelUpdateRequestDto, @PathVariable("id") Long id) {
        labelCommandService.updateLabel(labelUpdateRequestDto, id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabel(@PathVariable("id") Long id) {
        labelCommandService.deleteLabel(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
