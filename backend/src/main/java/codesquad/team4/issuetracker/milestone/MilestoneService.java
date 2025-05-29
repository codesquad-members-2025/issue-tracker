package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.entity.Milestone;
import codesquad.team4.issuetracker.exception.badrequest.InvalidCommentAccessException;
import codesquad.team4.issuetracker.exception.notfound.MilestoneNotFoundException;
import codesquad.team4.issuetracker.issue.IssueEvent;
import codesquad.team4.issuetracker.label.LabelRepository;
import codesquad.team4.issuetracker.milestone.dto.MilestoneRequestDto;
import codesquad.team4.issuetracker.milestone.dto.MilestoneResponseDto;
import codesquad.team4.issuetracker.milestone.dto.MilestoneResponseDto.MilestoneInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MilestoneService {
    private final MilestoneDao milestoneDao;
    private final MilestoneRepository milestoneRepository;
    private final ApplicationEventPublisher eventPublisher;

    public MilestoneResponseDto.MilestoneFilter getFilterMilestones() {
        List<MilestoneInfo> milestones = milestoneDao.findMilestoneForFiltering();

        return MilestoneResponseDto.MilestoneFilter.builder()
                .milestones(milestones)
                .count(milestones.size())
                .build();
    }

    public MilestoneResponseDto.MilestoneListDto getMilestones(boolean isOpen) {
        List<Map<String, Object>> rows = milestoneDao.findMilestonesByOpenStatus(isOpen);

        List<MilestoneResponseDto.MilestoneDto> result = new ArrayList<>();

        for (Map<String, Object> row :  rows) {
            int open = ((Number) row.get("open_issue_count")).intValue();
            int closed = ((Number) row.get("closed_issue_count")).intValue();

            double progress = 0.0;
            if (open+closed > 0) {
                double raw = closed * 100.0 / (open + closed);
                progress = Math.round(raw * 10.0) / 10.0; //소수점 1자리 반올림
            }

            MilestoneResponseDto.MilestoneDto dto = MilestoneResponseDto.MilestoneDto.builder()
                .id((Long) row.get("id"))
                .name((String) row.get("name"))
                .description((String) row.get("description"))
                .endDate(row.get("end_date") != null
                    ? ((Date) row.get("end_date")).toLocalDate()
                    : null)
                .progress(progress)
                .openIssues(open)
                .closedIssues(closed)
                .build();

            result.add(dto);
        }
        return MilestoneResponseDto.MilestoneListDto.builder()
            .milestones(result)
            .build();
    }

    @Transactional
    public void createMilestone(MilestoneRequestDto.CreateMilestoneDto request) {
        Milestone milestone = Milestone.builder()
                .name(request.getName())
                .description(request.getDescription())
                .endDate(request.getEndDate())
                .isOpen(true)
                .build();

        eventPublisher.publishEvent(new MilestoneEvent.Created());
        milestoneRepository.save(milestone);
    }

    @Transactional
    public void updateMilestone(Long milestoneId, MilestoneRequestDto.UpdateMilestoneDto request) {
        if (!milestoneRepository.existsById(milestoneId)) {
            throw new MilestoneNotFoundException(milestoneId);
        }

        Milestone updatedMilestone = Milestone.builder()
                .id(milestoneId)
                .name(request.getName())
                .description(request.getDescription())
                .endDate(request.getEndDate())
                .isOpen(true)
                .build();


        milestoneRepository.save(updatedMilestone);
    }

    @Transactional
    public void deleteMilestone(Long milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new MilestoneNotFoundException(milestoneId));

        boolean wasOpen = milestone.isOpen();
        milestoneRepository.deleteById(milestoneId);
        eventPublisher.publishEvent(new MilestoneEvent.Deleted(wasOpen));
    }

    @Transactional
    public void closeMilestone(Long milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
            .orElseThrow(() -> new MilestoneNotFoundException(milestoneId));

        boolean oldOpen = milestone.isOpen();
        milestone.updateStatus(false);
        milestoneRepository.save(milestone);
        boolean newOpen = milestone.isOpen();

        if (oldOpen != newOpen) {
            eventPublisher.publishEvent(
                    new MilestoneEvent.StatusChanged(oldOpen, false)
            );
        }

    }
}
