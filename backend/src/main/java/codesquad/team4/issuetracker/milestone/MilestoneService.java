package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.entity.Milestone;
import codesquad.team4.issuetracker.exception.notfound.MilestoneNotFoundException;
import codesquad.team4.issuetracker.label.LabelRepository;
import codesquad.team4.issuetracker.milestone.dto.MilestoneRequestDto;
import codesquad.team4.issuetracker.milestone.dto.MilestoneResponseDto;
import codesquad.team4.issuetracker.milestone.dto.MilestoneResponseDto.MilestoneInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MilestoneService {
    private final MilestoneDao milestoneDao;
    private final MilestoneRepository milestoneRepository;
    private final LabelRepository labelRepository;

    public MilestoneResponseDto.MilestoneFilter getFilterMilestones() {
        List<MilestoneInfo> milestones = milestoneDao.findMilestoneForFiltering();

        return MilestoneResponseDto.MilestoneFilter.builder()
                .milestones(milestones)
                .count(milestones.size())
                .build();
    }

    public MilestoneResponseDto.MilestoneCountDto getMilestoneCount() {
        Integer openCount = milestoneDao.countMilestonesByOpenStatus(true);
        Integer closedCount = milestoneDao.countMilestonesByOpenStatus(false);

        return MilestoneResponseDto.MilestoneCountDto.builder()
            .openCount(openCount != null ? openCount : 0)
            .closedCount(closedCount != null ? closedCount : 0)
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
            .build();

        milestoneRepository.save(milestone);
    }

    @Transactional
    public void updateMilestone(Long milestoneId, MilestoneRequestDto.CreateMilestoneDto request) {
        milestoneRepository.findById(milestoneId)
            .orElseThrow(() -> new MilestoneNotFoundException(milestoneId));

        Milestone updatedMilestone = Milestone.builder()
            .id(milestoneId)
            .name(request.getName())
            .description(request.getDescription())
            .endDate(request.getEndDate())
            .build();

        milestoneRepository.save(updatedMilestone);
    }

    @Transactional
    public void deleteMilestone(Long milestoneId) {
        if (!milestoneRepository.existsById(milestoneId)) {
            throw new MilestoneNotFoundException(milestoneId);
        }
        milestoneRepository.deleteById(milestoneId);
    }
}
