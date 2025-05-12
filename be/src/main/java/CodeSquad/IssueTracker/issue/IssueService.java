package CodeSquad.IssueTracker.issue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;

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
