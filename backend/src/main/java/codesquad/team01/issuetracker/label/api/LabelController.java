package codesquad.team01.issuetracker.label.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.team01.issuetracker.common.dto.ApiResponse;
import codesquad.team01.issuetracker.label.dto.LabelDto;
import codesquad.team01.issuetracker.label.service.LabelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class LabelController {

	private final LabelService labelService;

	@GetMapping("/labels")
	public ResponseEntity<ApiResponse<LabelDto.ListResponse>> getLabels() {
		LabelDto.ListResponse listResponse = labelService.getAllLabels();
		return ResponseEntity.ok(ApiResponse.success(listResponse));
	}

	@GetMapping("/filters/labels")
	public ResponseEntity<ApiResponse<LabelDto.LabelFilterListResponse>> getLabelFilter() {
		LabelDto.LabelFilterListResponse response = labelService.findLabelsForFilter();
		log.info("레이블 목록 개수= {}", response.totalCount());
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
