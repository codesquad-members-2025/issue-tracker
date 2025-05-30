package com.team5.issue_tracker.label.service;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team5.issue_tracker.common.exception.BaseException;
import com.team5.issue_tracker.common.exception.ErrorCode;
import com.team5.issue_tracker.common.exception.NotFoundException;
import com.team5.issue_tracker.label.domain.Label;
import com.team5.issue_tracker.label.dto.request.LabelRequest;
import com.team5.issue_tracker.label.repository.LabelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LabelService {
  private final LabelRepository labelRepository;

  @Transactional
  public Long createLabel(LabelRequest request) {
    Label label = new Label(
        request.getName(),
        request.getDescription(),
        request.getTextColor(),
        request.getBackgroundColor()
    );
    Label savedLabel = labelRepository.save(label);
    return savedLabel.getId();
  }

  @Transactional
  public void updateLabel(Long labelId, LabelRequest request) {
    Label label = labelRepository.findById(labelId)
        .orElseThrow(() -> new NotFoundException(ErrorCode.LABEL_NOT_FOUND));

    Label updatedLabel = new Label(
        label.getId(),
        request.getName(),
        request.getDescription(),
        request.getTextColor(),
        request.getBackgroundColor(),
        label.getCreatedAt(),
        Instant.now()
    );

    labelRepository.save(updatedLabel);
  }

  @Transactional
  public void deleteLabel(Long labelId) {
    if (!labelRepository.existsById(labelId)) {
      throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
    }
    labelRepository.deleteById(labelId);
  }
}
