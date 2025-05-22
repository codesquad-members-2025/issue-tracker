package codesquad.team4.issuetracker.aws;

import codesquad.team4.issuetracker.exception.ExceptionMessage;
import codesquad.team4.issuetracker.exception.badrequest.FileUploadException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3FileService {
    private static final String ACL_PUBLIC_READ = "public-read";
    private static final String S3_URL_FORMAT = "https://%s.s3.amazonaws.com/%s";
    private static final String UNDER_BAR = "_";
    public static final String EMPTY_STRING = "";

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file, String directory) {
        if (file == null || file.isEmpty()) return EMPTY_STRING;

        String originalFilename = file.getOriginalFilename();
        String key = directory + UUID.randomUUID() + UNDER_BAR + originalFilename;

        // S3에 파일을 업로드하기 위한 요청 객체 생성
        PutObjectRequest putObjectRequest = getRequest(file, key);

        try {
            // S3 클라이언트를 통해 파일 업로드
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (Exception e) {
            log.error("파일 업로드 중 오류 발생: {}", e.getMessage(), e);
            throw new FileUploadException(ExceptionMessage.FILE_UPLOAD_FAILED);
        }

        log.info("S3 업로드 성공");
        return String.format(S3_URL_FORMAT, bucket, key);
    }

    private PutObjectRequest getRequest(MultipartFile f, String key) {
        return PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .acl(ACL_PUBLIC_READ) // 퍼블릭 읽기 권한
                .contentType(f.getContentType())
                .build();
    }
}
