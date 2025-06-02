package codesquad.team01.issuetracker.issue.service;

import static codesquad.team01.issuetracker.issue.dto.IssueDto.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import codesquad.team01.issuetracker.common.exception.InvalidParameterException;
import codesquad.team01.issuetracker.milestone.exception.MilestoneNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum IssueUpdateCommand {
	TITLE(UpdateRequest::isUpdatingTitle) {
		@Override
		public void execute(
			IssueUpdateCommandFactory factory, Integer issueId, UpdateRequest request, LocalDateTime now) {
			factory.getIssueRepository().updateIssueTitle(issueId, request.title(), now);
		}
	},
	CONTENT(UpdateRequest::isUpdatingContent) {
		@Override
		public void execute(
			IssueUpdateCommandFactory factory, Integer issueId, UpdateRequest request, LocalDateTime now) {
			factory.getIssueRepository().updateIssueContent(issueId, request.content(), now);
		}
	},
	MILESTONE(UpdateRequest::isUpdatingMilestone) {
		@Override
		public void execute(
			IssueUpdateCommandFactory factory, Integer issueId, UpdateRequest request, LocalDateTime now) {
			if (request.milestoneId() != null &&
				!factory.getMilestoneRepository().existsMilestone(request.milestoneId())) {
				throw new MilestoneNotFoundException("존재하지 않는 마일스톤: " + request.milestoneId());
			}
			factory.getIssueRepository().updateIssueMilestone(issueId, request.milestoneId(), now);
		}
	},
	LABELS(UpdateRequest::isUpdatingLabels) {
		@Override
		public void execute(
			IssueUpdateCommandFactory factory, Integer issueId, UpdateRequest request, LocalDateTime now) {
			List<Integer> validLabelIds = factory.getLabelRepository().findValidLabelIds(request.getLabelIds());
			factory.getIssueRepository().removeLabelsFromIssue(issueId); // 전체 삭제 후
			if (!validLabelIds.isEmpty()) {
				factory.getIssueRepository().addLabelsToIssue(issueId, validLabelIds); // 덮어쓰기
			}
		}
	},
	ASSIGNEES(UpdateRequest::isUpdatingAssignees) {
		@Override
		public void execute(
			IssueUpdateCommandFactory factory, Integer issueId, UpdateRequest request, LocalDateTime now) {
			List<Integer> validAssigneeIds = factory.getUserRepository().findValidUserIds(request.getAssigneeIds());
			factory.getIssueRepository().removeAssigneesFromIssue(issueId);
			if (!validAssigneeIds.isEmpty()) {
				factory.getIssueRepository().addAssigneesToIssue(issueId, validAssigneeIds);
			}
		}
	};

	private final Predicate<UpdateRequest> checker;

	public boolean matches(UpdateRequest request) {
		return checker.test(request);
	}

	public abstract void execute(
		IssueUpdateCommandFactory factory, Integer issueId, UpdateRequest request, LocalDateTime now);

	public static IssueUpdateCommand findCommand(UpdateRequest request) {
		return Arrays.stream(values())
			.filter(command -> command.matches(request))
			.findFirst()
			.orElseThrow(() -> new InvalidParameterException("수정할 필드가 지정되지 않았습니다."));
	}
}
