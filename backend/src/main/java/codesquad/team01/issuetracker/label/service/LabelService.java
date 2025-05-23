package codesquad.team01.issuetracker.label.service;

import java.util.List;

import org.springframework.stereotype.Service;

import codesquad.team01.issuetracker.label.dto.LabelDto;
import codesquad.team01.issuetracker.label.repository.LabelQueryRepository;
import codesquad.team01.issuetracker.label.repository.LabelRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LabelService {

	private final LabelRepository labelRepository;
	private final LabelQueryRepository labelQueryRepository;

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

	public LabelDto.LabelFilterListResponse findLabelsForFilter() {
		List<LabelDto.LabelFilterResponse> labels = labelQueryRepository.findLabelsForFilter();

		return LabelDto.LabelFilterListResponse.builder()
			.totalCount(labels.size())
			.labels(labels)
			.build();
	}
}
