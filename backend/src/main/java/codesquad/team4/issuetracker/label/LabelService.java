package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.comment.dto.CommentRequestDto;
import codesquad.team4.issuetracker.entity.Label;
import codesquad.team4.issuetracker.exception.notfound.LabelNotFoundException;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.label.dto.LabelRequestDto;
import codesquad.team4.issuetracker.label.dto.LabelResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LabelService {
    private static final String CREATE_LABEL = "레이블이 생성되었습니다";

    private final LabelDao labelDao;
    private final LabelRepository labelRepository;

    public LabelResponseDto.LabelFilter getFilterLabels() {
        List<LabelResponseDto.LabelInfo> labels = labelDao.findLabelForFiltering();

        return LabelResponseDto.LabelFilter.builder()
                .labels(labels)
                .count(labels.size())
                .build();
    }

    @Transactional
    public void createLabel(LabelRequestDto.CreateLabelDto request) {
        Label label = Label.builder()
            .name(request.getName())
            .description(request.getDescription())
            .color(request.getColor())
            .build();

        labelRepository.save(label);
    }

    @Transactional
    public void updateLabel(Long labelId, LabelRequestDto.CreateLabelDto request) {
        labelRepository.findById(labelId)
            .orElseThrow(() -> new LabelNotFoundException(labelId));

        Label updatedLabel = Label.builder()
            .id(labelId)
            .name(request.getName())
            .description(request.getDescription())
            .color(request.getColor())
            .build();

        labelRepository.save(updatedLabel);
    }

    @Transactional
    public void deleteLabel(Long labelId) {
        if (!labelRepository.existsById(labelId)) {
            throw new LabelNotFoundException(labelId);
        }
    }
}
