package back.ailion.model.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class FileUploadRequest {

    private Long postId;
    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles;

    public FileUploadRequest(Long postId, MultipartFile attachFile, List<MultipartFile> imageFiles) {
        this.postId = postId;
        this.attachFile = attachFile;
        this.imageFiles = imageFiles;
    }

    public FileUploadRequest(Long postId, List<MultipartFile> imageFiles) {
        this.postId = postId;
        this.imageFiles = imageFiles;
    }
}
