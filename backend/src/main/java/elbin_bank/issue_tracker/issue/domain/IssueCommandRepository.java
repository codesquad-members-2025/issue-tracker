package elbin_bank.issue_tracker.issue.domain;

import elbin_bank.issue_tracker.issue.application.command.dto.IssueCreateCommand;

public interface IssueCommandRepository {

    Long create(IssueCreateCommand command);

}
