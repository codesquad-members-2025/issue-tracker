package CodeSquad.IssueTracker.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface Uploader {
    String upload(MultipartFile file) throws IOException;
}
