package codesquad.team01.issuetracker.label.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import codesquad.team01.issuetracker.label.dto.LabelDto;
import codesquad.team01.issuetracker.label.repository.LabelRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LabelService {

	private final LabelRepository labelRepository;

	public LabelDto.ListResponse getLabels() {
		List<LabelDto.ListItemResponse> items = labelRepository.findLabels()
			.stream()
			.map(LabelDto.ListItemResponse::from)
			.collect(Collectors.toList());
		return new LabelDto.ListResponse(items.size(), items);
	}

	public LabelDto.LabelFilterListResponse findLabelsForFilter() {
		List<LabelDto.LabelFilterResponse> labels = labelRepository.findLabelsForFilter();

		return LabelDto.LabelFilterListResponse.builder()
			.totalCount(labels.size())
			.labels(labels)
			.build();
	}
}
