package codesquad.team01.issuetracker.milestone.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.team01.issuetracker.common.exception.DuplicateMilestoneTitleException;
import codesquad.team01.issuetracker.common.exception.InvalidDateException;
import codesquad.team01.issuetracker.common.exception.MilestoneNotFoundException;
import codesquad.team01.issuetracker.issue.repository.IssueRepository;
import codesquad.team01.issuetracker.milestone.domain.Milestone;
import codesquad.team01.issuetracker.milestone.domain.MilestoneState;
import codesquad.team01.issuetracker.milestone.dto.MilestoneDto;
import codesquad.team01.issuetracker.milestone.repository.MilestoneRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MilestoneService {

	private final MilestoneRepository milestoneRepository;
	private final IssueRepository issueRepository;

	public MilestoneDto.MilestoneFilterListResponse findMilestonesForFilter() {

		List<MilestoneDto.MilestoneFilterResponse> milestones =
			milestoneRepository.findMilestonesForFilter();

		return MilestoneDto.MilestoneFilterListResponse.builder()
			.totalCount(milestones.size())
			.milestones(milestones)
			.build();
	}

	public MilestoneDto.ListResponse getMilestones(MilestoneState state) {
		List<MilestoneDto.MilestoneRow> rows = milestoneRepository.findByState(state);

		List<Integer> milestoneIds = rows.stream()
			.map(MilestoneDto.MilestoneRow::id)
			.toList();

		if (milestoneIds.isEmpty()) {
			return new MilestoneDto.ListResponse(0, List.of());
		}

		List<MilestoneDto.MilestoneIssueCount> issueCounts = issueRepository.countByMilestoneIds(milestoneIds);

		Map<Integer, MilestoneDto.MilestoneIssueCount> countMap = issueCounts.stream()
			.collect(Collectors.toMap(
				MilestoneDto.MilestoneIssueCount::milestoneId,
				Function.identity()
			));

		List<MilestoneDto.MilestoneListItem> items = rows.stream()
			.map(row -> {
				MilestoneDto.MilestoneIssueCount count = countMap.getOrDefault(row.id(),
					new MilestoneDto.MilestoneIssueCount(
						row.id(), 0, 0));
				return MilestoneDto.MilestoneListItem.from(row, count);
			})
			.toList();

		return new MilestoneDto.ListResponse(items.size(), items);
	}

	@Transactional
	public MilestoneDto.MilestoneCreateResponse createMilestone(MilestoneDto.MilestoneCreateRequest request) {
		LocalDate dueDate = parseDueDate(request.dueDate());
		String milestoneTitle = request.title();

		if (milestoneRepository.existsByTitle(milestoneTitle)) {
			throw new DuplicateMilestoneTitleException(milestoneTitle);
		}

		Milestone entity = Milestone.builder()
			.title(request.title())
			.description(request.description())
			.dueDate(dueDate)
			.state(MilestoneState.OPEN)
			.build();

		Milestone savedMilestone = milestoneRepository.save(entity);

		return MilestoneDto.MilestoneCreateResponse.from(savedMilestone);
	}

	private LocalDate parseDueDate(String inputDueDate) {
		if (inputDueDate == null || inputDueDate.isBlank()) {
			return null;
		}
		try {
			return LocalDate.parse(inputDueDate);
		} catch (DateTimeParseException e) {
			throw new InvalidDateException("잘못된 마감일 형식입니다. 연월일을 확인하세요.");
		}
	}

	@Transactional
	public MilestoneDto.MilestoneUpdateResponse updateMilestone(int id, MilestoneDto.MilestoneUpdateRequest request) {
		String newTitle = request.title();
		LocalDate newDueDate = parseDueDate(request.dueDate());
		MilestoneState newState = MilestoneState.fromStateStr(request.state());

		Milestone updateTarget = milestoneRepository.findById(id)
			.orElseThrow(() -> new MilestoneNotFoundException(id));

		if (milestoneRepository.existsByTitleAndIdNot(newTitle, id)) {
			throw new DuplicateMilestoneTitleException(newTitle);
		}

		updateTarget.update(newTitle, request.description(), newDueDate, newState);
		Milestone saved = milestoneRepository.save(updateTarget);

		return MilestoneDto.MilestoneUpdateResponse.from(saved);
	}

	@Transactional
	public void deleteMilestone(int id) {
		Milestone targetMilestone = milestoneRepository.findById(id)
			.orElseThrow(() -> new MilestoneNotFoundException(id));

		targetMilestone.delete();
		milestoneRepository.save(targetMilestone);
	}
}
