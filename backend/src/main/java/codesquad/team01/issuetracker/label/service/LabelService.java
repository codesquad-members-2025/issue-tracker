package codesquad.team01.issuetracker.label.service;

import java.util.List;

import org.springframework.stereotype.Service;

import codesquad.team01.issuetracker.label.dto.LabelDto;
import codesquad.team01.issuetracker.label.repository.LabelRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LabelService {

	private final LabelRepository labelRepository;

	public List<LabelDto.ListResponse> getAllLabels() {
		var items = labelRepository.findAllLabels().stream()
			.map(label -> new LabelDto.ListItemResponse(
				label.id(),
				label.name(),
				label.description(),
				label.color(),
				label.textColor()
			))
			.toList();
		return List.of(new LabelDto.ListResponse(items));
	}
}
