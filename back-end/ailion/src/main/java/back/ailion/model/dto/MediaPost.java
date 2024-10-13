package back.ailion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaPost {

    private PostDto postDto;
    private FileUploadResponse fileUploadResponse;
}
