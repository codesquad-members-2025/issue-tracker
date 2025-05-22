package codesquad.team01.issuetracker.milestone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import codesquad.team01.issuetracker.milestone.dto.MilestoneDto;
import codesquad.team01.issuetracker.milestone.repository.MilestoneQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MilestoneService {

	private final MilestoneQueryRepository milestoneQueryRepository;

	public MilestoneDto.MilestoneFilterListResponse findMilestonesForFilter() {

		List<MilestoneDto.MilestoneFilterResponse> milestones =
			milestoneQueryRepository.findMilestonesForFilter();

		return MilestoneDto.MilestoneFilterListResponse.builder()
			.totalCount(milestones.size())
			.milestones(milestones)
			.build();
	}
}
