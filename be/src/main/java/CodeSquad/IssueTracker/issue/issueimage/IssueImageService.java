package CodeSquad.IssueTracker.issue.issueimage;

import CodeSquad.IssueTracker.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueImageService {

    private final S3Uploader s3Uploader;
    private final IssueImageRepository issueImageRepository;

    public void uploadAndSaveImages(Long issueId, List<MultipartFile> files) {
        if (files == null || files.isEmpty()) return;

        for (MultipartFile file : files) {
            try {
                // 1. S3 업로드
                String imageUrl = s3Uploader.upload(file);

                // 2. DB 저장
                IssueImage image = new IssueImage();
                image.setIssueId(issueId);
                image.setImageUrl(imageUrl);
                image.setOriginalName(file.getOriginalFilename());
                image.setContentType(file.getContentType());

                issueImageRepository.save(image);

            } catch (IOException e) {
                throw new RuntimeException("이미지 업로드 실패: " + file.getOriginalFilename(), e);
            }
        }
    }

    public List<IssueImage> getImagesByIssue(Long issueId) {
        return issueImageRepository.findByIssueId(issueId);
    }
}
