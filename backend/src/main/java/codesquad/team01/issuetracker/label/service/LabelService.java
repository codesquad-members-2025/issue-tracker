package codesquad.team01.issuetracker.label.service;

import org.springframework.stereotype.Service;

import codesquad.team01.issuetracker.label.dto.LabelDto;
import codesquad.team01.issuetracker.label.repository.LabelRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LabelService {

	private final LabelRepository labelRepository;

	public LabelDto.ListResponse getAllLabels() {
		var items = labelRepository.findAllLabels().stream()
			.map(label -> new LabelDto.ListItemResponse(
				label.id(),
				label.name(),
				label.description(),
				label.color(),
				label.textColor()
			))
			.toList();
		return new LabelDto.ListResponse(items);
	}
}
