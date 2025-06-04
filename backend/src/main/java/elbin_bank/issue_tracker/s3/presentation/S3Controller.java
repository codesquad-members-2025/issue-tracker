package elbin_bank.issue_tracker.s3.presentation;

import elbin_bank.issue_tracker.s3.application.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/s3/")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @GetMapping("/presigned-url")
    public ResponseEntity<String> getPresignedUrl(@RequestParam String filename,
                                                  @RequestParam("Content-Type") String contentType) {
        String url = s3Service.getPreSignedUrl(filename, contentType);
        return ResponseEntity.ok(url);
    }

}
