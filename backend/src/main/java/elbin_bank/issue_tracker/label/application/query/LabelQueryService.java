package elbin_bank.issue_tracker.label.application.query;

import elbin_bank.issue_tracker.label.application.query.dto.LabelsResponseDto;
import elbin_bank.issue_tracker.label.application.query.repository.LabelQueryRepository;
import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelQueryService {

    private final LabelQueryRepository labelQueryRepository;

    public LabelsResponseDto findAll() {
        List<LabelProjection> labels = labelQueryRepository.findAll();

        return new LabelsResponseDto(labels);
    }

}
