package back.ailion.controller;

import back.ailion.model.dto.DownloadDto;
import back.ailion.model.dto.FileUploadResponse;
import back.ailion.model.dto.request.FileUploadRequest;
import back.ailion.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/ailion/files")
@RequiredArgsConstructor
public class AwsS3ApiController {

    private final AwsS3Service awsS3Service;

    @PostMapping("/upload/{postId}")
    public FileUploadResponse uploadFile(@PathVariable("postId") Long postId,
                                         @RequestPart(value = "attachFile") MultipartFile attachFile,
                                         @RequestPart(value = "imageFiles") List<MultipartFile> imageFiles) throws IOException {

        FileUploadRequest fileUploadRequest = new FileUploadRequest(postId, attachFile, imageFiles);
        return awsS3Service.uploadFile(fileUploadRequest);
    }

    // 이미지 다운로드 컨트롤러
    @GetMapping("/image/download/{filename}")
    public ResponseEntity<Resource> downloadImageFile(@PathVariable String filename) throws MalformedURLException {

        DownloadDto downloadDto = awsS3Service.downloadImageFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, downloadDto.getContentDisposition())
                .body(downloadDto.getResource());

    }

    // 첨부파일 다운로드 컨트롤러
    @GetMapping("/attachment/download/{imageId}")
    public ResponseEntity<Resource> downloadAttachFile(@PathVariable Long imageId) throws MalformedURLException {

        DownloadDto downloadDto = awsS3Service.downloadAttachFile(imageId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, downloadDto.getContentDisposition())
                .body(downloadDto.getResource());
    }
}
