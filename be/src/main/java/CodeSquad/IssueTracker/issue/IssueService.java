package CodeSquad.IssueTracker.issue;

import CodeSquad.IssueTracker.issue.dto.IssueCreateRequest;
import CodeSquad.IssueTracker.issue.dto.IssueUpdateDto;
import CodeSquad.IssueTracker.issueAssignee.IssueAssignee;
import CodeSquad.IssueTracker.issueAssignee.IssueAssigneeService;
import CodeSquad.IssueTracker.issueLabel.IssueLabel;
import CodeSquad.IssueTracker.issueLabel.IssueLabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final IssueAssigneeService issueAssigneeService;
    private final IssueLabelService issueLabelService;

    public Issue save(Issue issue){
        return issueRepository.save(issue);
    }

    public void update(Long issueId, IssueUpdateDto updateParam){
        issueRepository.update(issueId, updateParam);
    }

    public Optional<Issue> findById(Long issueId){
        return issueRepository.findById(issueId);
    }

    public Iterable<Issue> findAll(){
        return issueRepository.findAll();
    }

    public Issue createIssue(IssueCreateRequest request) {
        // 1. 이슈 저장
        Issue issue = new Issue();
        issue.setTitle(request.getTitle());
        issue.setContent(request.getContent());
        issue.setMilestoneId(request.getMilestoneId());

        Issue savedIssue = issueRepository.save(issue);

        // 2. 이슈-담당자 관계 저장
        if (!request.getAssigneeIds().isEmpty()) {
            issueAssigneeService.assignAssignees(savedIssue.getIssueId(), request.getAssigneeIds());
        }

        // 3. 라벨 관계 저장
        if(!request.getLabelIds().isEmpty()){
            issueLabelService.assignLabels(savedIssue.getIssueId(), request.getLabelIds());
        }

        // 4. 이미지 URL 저장 로직 추가해야함 하 언제하냐

        return savedIssue;
    }





}
