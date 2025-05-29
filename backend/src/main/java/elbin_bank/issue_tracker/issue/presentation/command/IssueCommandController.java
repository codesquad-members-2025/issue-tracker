package elbin_bank.issue_tracker.issue.presentation.command;

import elbin_bank.issue_tracker.issue.application.command.IssueCommandService;
import elbin_bank.issue_tracker.issue.application.command.dto.IssueCreateResponseDto;
import elbin_bank.issue_tracker.issue.presentation.command.dto.request.IssueCreateRequestDto;
import elbin_bank.issue_tracker.issue.presentation.command.dto.request.IssueStateUpdateRequestDto;
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
    public ResponseEntity<IssueCreateResponseDto> createIssue(@RequestBody IssueCreateRequestDto issueCreateRequestDto) {
        IssueCreateResponseDto issueCreateResponseDto = issueCommandService.createIssue(issueCreateRequestDto);

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

}
