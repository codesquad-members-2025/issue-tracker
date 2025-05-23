package codesquad.team01.issuetracker.label.service;

import java.util.List;

import org.springframework.stereotype.Service;

import codesquad.team01.issuetracker.label.dto.LabelDto;
import codesquad.team01.issuetracker.label.repository.LabelQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LabelService {

	private final LabelQueryRepository labelQueryRepository;

	public LabelDto.LabelFilterListResponse findLabelsForFilter() {

		List<LabelDto.LabelFilterResponse> labels = labelQueryRepository.findLabelsForFilter();

		return LabelDto.LabelFilterListResponse.builder()
			.totalCount(labels.size())
			.labels(labels)
			.build();
	}

}
