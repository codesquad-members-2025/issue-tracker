package codesquad.team01.issuetracker.label.service;

import java.util.List;
import java.util.stream.Collectors;

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

	public LabelDto.ListResponse getLabels() {
		List<LabelDto.ListItemResponse> items = labelRepository.findLabels()
			.stream()
			.map(this::toListItemResponse)
			.collect(Collectors.toList());
		return new LabelDto.ListResponse(items.size(), items);
	}

	private LabelDto.ListItemResponse toListItemResponse(LabelDto.LabelRow row) {
		return LabelDto.ListItemResponse.builder()
			.id(row.id())
			.name(row.name())
			.description(row.description())
			.color(row.color())
			.textColor(row.textColor())
			.build();
	}

	public LabelDto.LabelFilterListResponse findLabelsForFilter() {
		List<LabelDto.LabelFilterResponse> labels = labelQueryRepository.findLabelsForFilter();

		return LabelDto.LabelFilterListResponse.builder()
			.totalCount(labels.size())
			.labels(labels)
			.build();
	}
}
