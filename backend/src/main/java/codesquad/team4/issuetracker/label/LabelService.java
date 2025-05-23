package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.comment.dto.CommentRequestDto;
import codesquad.team4.issuetracker.entity.Label;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.label.dto.LabelRequestDto;
import codesquad.team4.issuetracker.label.dto.LabelResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


}
