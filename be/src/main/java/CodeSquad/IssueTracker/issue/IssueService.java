package CodeSquad.IssueTracker.issue;

import CodeSquad.IssueTracker.label.Label;
import CodeSquad.IssueTracker.label.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final LabelRepository labelRepository;

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





}
