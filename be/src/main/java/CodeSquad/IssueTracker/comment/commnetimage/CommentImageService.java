package CodeSquad.IssueTracker.comment.commnetimage;

import CodeSquad.IssueTracker.util.S3Uploader;
import CodeSquad.IssueTracker.util.Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentImageService {

    private final Uploader uploader;
    private final CommentImageRepository commentImageRepository;

    public void uploadAndSaveImages(Long commentId, List<MultipartFile> files) {
        if(files == null || files.isEmpty()) return;

        for (MultipartFile file : files) {
            try{

                String imageUrl = uploader.upload(file);

                CommentImage commentImage = new CommentImage();
                commentImage.setCommentId(commentId);
                commentImage.setImageUrl(imageUrl);
                commentImage.setOriginalName(file.getOriginalFilename());
                commentImage.setContentType(file.getContentType());

                commentImageRepository.save(commentImage);
            } catch (IOException e){
                throw new RuntimeException("이미지 업로드 실패: " + file.getOriginalFilename(), e);
            }
        }
    }

    public CommentImage getCommentImageByCommentId(Long commentId){
       return commentImageRepository.findByCommentId(commentId);
    }
}
