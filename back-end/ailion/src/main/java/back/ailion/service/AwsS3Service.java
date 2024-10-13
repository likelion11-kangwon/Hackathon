package back.ailion.service;

import back.ailion.exception.BaseExceptionCode;
import back.ailion.exception.custom.FileException;
import back.ailion.exception.custom.NotFoundException;
import back.ailion.model.dto.DownloadDto;
import back.ailion.model.dto.FileUploadResponse;
import back.ailion.model.dto.request.FileUploadRequest;
import back.ailion.model.entity.FileUpload;
import back.ailion.model.entity.Image;
import back.ailion.model.entity.Post;
import back.ailion.repository.ImageRepository;
import back.ailion.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final S3CommonUtils commonUtils;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public FileUploadResponse uploadFile(FileUploadRequest fileUploadRequest) throws IOException {

        Post post = postRepository.findById(fileUploadRequest.getPostId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.POST_NOT_FOUND));

        // 파일이 들어있는지 확인하는 메서드
//        validateFileExists(fileUploadRequest.getAttachFile());
//        validateFilesExists(fileUploadRequest.getImageFiles());

        // MultipartFile을 FileUpload로 변환
        FileUpload attachFile = null;
        if (fileUploadRequest.getAttachFile() != null) {
            attachFile = commonUtils.convertFile(fileUploadRequest.getAttachFile());
        }

        List<FileUpload> storeImageFiles = new ArrayList<>();
        List<String> fileUrls = new ArrayList<>();
        if (fileUploadRequest.getImageFiles() != null) {
            storeImageFiles = commonUtils.convertFiles(fileUploadRequest.getImageFiles());
            fileUrls = commonUtils.storeFiles(fileUploadRequest.getImageFiles());
        }

        if (attachFile != null || storeImageFiles != null) {
            Image image = Image.builder()
                    .post(post)
                    .attachFile(attachFile)
                    .imageFiles(storeImageFiles)
                    .build();

            imageRepository.save(image);
        }

        return new FileUploadResponse(attachFile != null ? commonUtils.storeFile(fileUploadRequest.getAttachFile(), attachFile.getStoreFileName()) : null, fileUrls);
    }

    @Transactional
    public FileUploadResponse changeProfileImage(MultipartFile multipartFile) throws IOException {

        // 파일이 들어있는지 확인하는 메서드
        validateFileExists(multipartFile);

        // MultipartFile을 FileUpload로 변환
        FileUpload profileImage = commonUtils.convertFile(multipartFile);

        Image image = Image.builder()
                .attachFile(profileImage)
                .build();
        imageRepository.save(image);

        String storeFileName = profileImage.getStoreFileName();

        return new FileUploadResponse(commonUtils.storeFile(multipartFile, storeFileName));
    }

    public DownloadDto downloadImageFile(String filename) throws MalformedURLException {

        commonUtils.validateFileExistsAtUrl(filename);

        UrlResource resource = new UrlResource(commonUtils.getFullPath(filename));

        String encodedUploadFileName = UriUtils.encode(filename, StandardCharsets.UTF_8);
        String contentDisposition = "image; filename=\"" + encodedUploadFileName + "\"";

        return new DownloadDto(contentDisposition, resource);
    }

    public DownloadDto downloadAttachFile(Long imageId) throws MalformedURLException {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.FILE_NOT_FOUND));

        String storeFileName = image.getAttachFile().getStoreFileName();
        String uploadFileName = image.getAttachFile().getUploadFileName();

        commonUtils.validateFileExistsAtUrl(storeFileName);

        UrlResource resource = new UrlResource(commonUtils.getFullPath(storeFileName));

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return new DownloadDto(contentDisposition, resource);
    }


    public void validateFileExists(MultipartFile multipartFile) {

        // 업로드된 파일이 없는 경우
        if (multipartFile == null) {
            throw new FileException(BaseExceptionCode.MISSING_FILE);
        }

        // 업로드된 파일이 비어 있는 경우
        if (multipartFile.isEmpty()) {
            throw new FileException(BaseExceptionCode.EMPTY_FILE);
        }
    }

    public void validateFilesExists(List<MultipartFile> multipartFiles) {

        for (MultipartFile multipartFile : multipartFiles) {
            validateFileExists(multipartFile);
        }
    }
}
