package com.team5.issue_tracker.label.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team5.issue_tracker.label.domain.Label;
import com.team5.issue_tracker.label.dto.request.LabelCreateRequest;
import com.team5.issue_tracker.label.repository.LabelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LabelService {
  private final LabelRepository labelRepository;

  @Transactional
  public Long createLabel(LabelCreateRequest request) {
    Label label = new Label(
        request.getName(),
        request.getDescription(),
        request.getTextColor(),
        request.getBackgroundColor()
    );
    Label savedLabel = labelRepository.save(label);
    return savedLabel.getId();
  }
}
