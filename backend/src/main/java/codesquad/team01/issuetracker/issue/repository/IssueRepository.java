package codesquad.team01.issuetracker.issue.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import codesquad.team01.issuetracker.issue.domain.Issue;
import codesquad.team01.issuetracker.milestone.dto.MilestoneDto;

public interface IssueRepository extends CrudRepository<Issue, Integer>, IssueQueryRepository {

	// 마일스톤 id 리스트로 MilestoneIssueCount 의 리스트 알아내기
	@Query("""
		SELECT
			i.milestone_id											AS milestoneId,
			SUM(CASE WHEN i.state = 'OPEN' THEN 1 ELSE 0 END)		AS openCount,
			SUM(CASE WHEN i.state = 'CLOSE' THEN 1 ELSE 0 END)		AS closedCount
		FROM issue i
		WHERE i.milestone_id IN (:milestoneIds)
		GROUP BY i.milestone_id
		""")
	List<MilestoneDto.MilestoneIssueCount> countByMilestoneIds(@Param("milestoneIds") List<Integer> milestoneIds);
}
