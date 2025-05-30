package elbin_bank.issue_tracker.s3.application;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    private static final String PREFIX = "uploads";

    /**
     * 파일 이름을 받아 Presigned URL 발급
     */
    public String getPreSignedUrl(String originalFileName, String contentType) {
        String path = generateUniquePath(originalFileName);

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, path)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getExpiration())
                .withContentType(contentType);

        URL url = amazonS3.generatePresignedUrl(request);

        return url.toString();
    }

    private String generateUniquePath(String originalFileName) {
        String uuid = UUID.randomUUID().toString();

        return String.format("%s/%s%s", PREFIX, uuid, originalFileName);
    }

    private Date getExpiration() {
        return new Date(System.currentTimeMillis() + 1000 * 60 * 2); // 2분
    }

}
