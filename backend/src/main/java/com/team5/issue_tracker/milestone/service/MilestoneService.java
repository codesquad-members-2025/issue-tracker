package com.team5.issue_tracker.milestone.service;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team5.issue_tracker.common.exception.ErrorCode;
import com.team5.issue_tracker.common.exception.NotFoundException;
import com.team5.issue_tracker.milestone.domain.Milestone;
import com.team5.issue_tracker.milestone.dto.request.MilestoneRequest;
import com.team5.issue_tracker.milestone.repository.MilestoneRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MilestoneService {
  private final MilestoneRepository milestoneRepository;

  @Transactional
  public Long createMilestone(MilestoneRequest request) {
    Milestone milestone = new Milestone(
        request.getName(),
        request.getDeadline(),
        request.getDescription(),
        true
    );

    Milestone savedMilestone = milestoneRepository.save(milestone);
    return savedMilestone.getId();
  }

  @Transactional
  public void deleteMilestoneById(Long id) {
    if (!milestoneRepository.existsById(id)) {
      throw new NotFoundException(ErrorCode.MILESTONE_NOT_FOUND);
    }
    milestoneRepository.deleteById(id);
  }

  @Transactional
  public void updateMilestone(Long milestoneId, MilestoneRequest request) {
    Milestone milestone = milestoneRepository.findById(milestoneId)
        .orElseThrow(() -> new NotFoundException(ErrorCode.MILESTONE_NOT_FOUND));

    Milestone updatedMilestone = new Milestone(
        milestone.getId(),
        request.getName(),
        request.getDeadline(),
        request.getDescription(),
        milestone.getIsOpen(),
        milestone.getCreatedAt(),
        Instant.now()
    );

    milestoneRepository.save(updatedMilestone);
  }
}
