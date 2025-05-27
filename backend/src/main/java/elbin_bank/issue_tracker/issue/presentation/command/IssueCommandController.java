package elbin_bank.issue_tracker.issue.presentation.command;

import elbin_bank.issue_tracker.issue.application.command.IssueCommandService;
import elbin_bank.issue_tracker.issue.application.command.dto.IssueCreateResponseDto;
import elbin_bank.issue_tracker.issue.presentation.command.dto.request.IssueCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
