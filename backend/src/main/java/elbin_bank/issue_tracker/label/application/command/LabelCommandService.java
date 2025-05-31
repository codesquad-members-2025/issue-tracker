package elbin_bank.issue_tracker.label.application.command;

import elbin_bank.issue_tracker.comment.exception.CommentNotFoundException;
import elbin_bank.issue_tracker.label.application.query.repository.LabelQueryRepository;
import elbin_bank.issue_tracker.label.domain.Label;
import elbin_bank.issue_tracker.label.domain.LabelCommandRepository;
import elbin_bank.issue_tracker.label.exception.LabelNotFoundException;
import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;
import elbin_bank.issue_tracker.label.presentation.command.dto.request.LabelCreateRequestDto;
import elbin_bank.issue_tracker.label.presentation.command.dto.request.LabelUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LabelCommandService {

    private final LabelCommandRepository labelCommandRepository;
    private final LabelQueryRepository labelQueryRepository;

    @Transactional
    public void createLabel(LabelCreateRequestDto labelCreateRequestDto) {
        Label label = Label.of(labelCreateRequestDto.name(), labelCreateRequestDto.description(), labelCreateRequestDto.color());

        labelCommandRepository.save(label);
    }

    @Transactional
    public void updateLabel(LabelUpdateRequestDto labelUpdateRequestDto, Long id) {
        LabelProjection label = Optional.ofNullable(labelQueryRepository.findById(id))
                .orElseThrow(() -> new LabelNotFoundException(id));

        labelCommandRepository.update(label, labelUpdateRequestDto);
    }
}
