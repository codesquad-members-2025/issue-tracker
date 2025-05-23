package codesquad.team01.issuetracker.label.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import codesquad.team01.issuetracker.label.domain.Label;
import codesquad.team01.issuetracker.label.dto.LabelDto;

@Repository
public interface LabelRepository extends CrudRepository<Label, Integer> {
	@Query("""
		SELECT 
			id,
			name,
			description,
			color,
			text_color
		FROM label
		WHERE deleted_at IS NULL
		""")
	List<LabelDto.ListItemResponse> findLabels();
}
