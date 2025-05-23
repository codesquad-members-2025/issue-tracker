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

	public LabelDto.ListResponse getLabels() {
		List<LabelDto.ListItemResponse> items = labelRepository.findLabels();
		return new LabelDto.ListResponse(items.size(), items);
	}

	public LabelDto.LabelFilterListResponse findLabelsForFilter() {
		List<LabelDto.LabelFilterResponse> labels = labelQueryRepository.findLabelsForFilter();

		return LabelDto.LabelFilterListResponse.builder()
			.totalCount(labels.size())
			.labels(labels)
			.build();
	}
}
