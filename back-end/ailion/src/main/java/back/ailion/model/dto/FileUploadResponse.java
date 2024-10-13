package back.ailion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {

    String attachFileName;
    List<String> imageFileUrls;

    public FileUploadResponse(String attachFileName) {
        this.attachFileName = attachFileName;
    }
}
