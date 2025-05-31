package elbin_bank.issue_tracker.issue.presentation.command;

import elbin_bank.issue_tracker.issue.application.command.IssueCommandService;
import elbin_bank.issue_tracker.issue.application.command.dto.IssueCreateResponseDto;
import elbin_bank.issue_tracker.issue.presentation.command.dto.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/issues")
@RequiredArgsConstructor
public class IssueCommandController {

    private final IssueCommandService issueCommandService;

    @PostMapping("")
    public ResponseEntity<IssueCreateResponseDto> createIssue(@RequestBody IssueCreateRequestDto issueCreateRequestDto,
                                                              @RequestAttribute("user") Long userId) {
        IssueCreateResponseDto issueCreateResponseDto = issueCommandService.createIssue(issueCreateRequestDto, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(issueCreateResponseDto);
    }

    @PatchMapping("/{id}/state")
    public ResponseEntity<Void> updateState(
            @PathVariable Long id,
            @RequestBody IssueStateUpdateRequestDto dto
    ) {
        issueCommandService.updateState(id, dto);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/title")
    public ResponseEntity<Void> updateTitle(
            @PathVariable Long id,
            @RequestBody IssueTitleUpdateRequestDto dto
    ) {
        issueCommandService.updateTitle(id, dto);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/content")
    public ResponseEntity<Void> updateContent(
            @PathVariable Long id,
            @RequestBody IssueContentUpdateRequestDto dto
    ) {
        issueCommandService.updateContent(id, dto);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/milestone")
    public ResponseEntity<Void> updateMilestone(
            @PathVariable Long id,
            @RequestBody IssueMilestoneUpdateRequestDto dto
    ) {
        issueCommandService.updateMilestone(id, dto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/assignees")
    public ResponseEntity<Void> updateAssignees(
            @PathVariable Long id,
            @RequestBody IssueAssigneesUpdateRequestDto dto
    ) {
        issueCommandService.updateAssignees(id, dto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/labels")
    public ResponseEntity<Void> updateLabels(
            @PathVariable Long id,
            @RequestBody IssueLabelsUpdateRequestDto dto
    ) {
        issueCommandService.updateLabels(id, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id,
                                            @RequestAttribute("user") long userId) {
        issueCommandService.deleteIssue(id, userId);

        return ResponseEntity.noContent().build();
    }

}
