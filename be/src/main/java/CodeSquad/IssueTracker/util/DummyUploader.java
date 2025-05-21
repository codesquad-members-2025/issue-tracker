package CodeSquad.IssueTracker.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Profile("local")
@Service
public class DummyUploader implements Uploader {
    @Override
    public String upload(MultipartFile file) {
        System.out.println("📦 Dummy upload: " + file.getOriginalFilename());
        return "https://dummy.local/" + file.getOriginalFilename();
    }
}

