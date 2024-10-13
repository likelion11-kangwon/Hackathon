package back.ailion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.UrlResource;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadDto {

    String contentDisposition;
    UrlResource resource;
}

