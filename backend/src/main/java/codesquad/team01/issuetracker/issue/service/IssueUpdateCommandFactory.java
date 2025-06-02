package codesquad.team01.issuetracker.issue.service;

import org.springframework.stereotype.Component;

import codesquad.team01.issuetracker.issue.dto.IssueDto;
import codesquad.team01.issuetracker.issue.repository.IssueRepository;
import codesquad.team01.issuetracker.label.repository.LabelRepository;
import codesquad.team01.issuetracker.milestone.repository.MilestoneRepository;
import codesquad.team01.issuetracker.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Component
public class IssueUpdateCommandFactory {
	private final IssueRepository issueRepository;
	private final LabelRepository labelRepository;
	private final UserRepository userRepository;
	private final MilestoneRepository milestoneRepository;

	public IssueUpdateCommand getCommand(IssueDto.UpdateRequest request) {
		return IssueUpdateCommand.findCommand(request);
	}
}
