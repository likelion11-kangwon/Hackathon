package back.ailion.service;

import back.ailion.exception.BaseExceptionCode;
import back.ailion.exception.custom.FileException;
import back.ailion.exception.custom.NotFoundException;
import back.ailion.model.entity.FileUpload;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class S3CommonUtils {

    private final String endPoint = "https://kr.object.ncloudstorage.com";
    private final String regionName = "kr-standard";
    private final String accessKey = "QEHyLagTdadMDvtKmj7s";
    private final String secretKey = "VWQgtAGbqQoA59Tr71PiZSjX2DoOi7SrNEwcLpF3";
    private final String bucketName = "sample-ai-project";


    // S3 client
    private final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
            .build();
    //    public Bucket createBucket() {
//        Bucket newBucket = s3.createBucket("생성할 버켓이름");
//        return newBucket;
//    }

    @Value("${file.dir}")
    private String fileDir;
    // 파일 이름을 받아서 fullPath를 반환
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<FileUpload> convertFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<FileUpload> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(convertFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public FileUpload convertFile(MultipartFile multipartFile) throws IOException {

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        return new FileUpload(originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    // 확장자를 추출하는 메소드
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    // 첨부 파일 스토리지에 저장
    public String storeFile(MultipartFile multipartFile, String storeFileName) {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            s3.putObject(new PutObjectRequest(bucketName, storeFileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new FileException(BaseExceptionCode.FILE_UPLOAD_FAILED, e);
        }

        return s3.getUrl(bucketName, storeFileName).toString();
    }

    // 복수 이미지 파일 스토리지에 저장
    public List<String> storeFiles(List<MultipartFile> multipartFiles) throws IOException {

        List<String> fileUrls = new ArrayList<>();

        // 파일 업로드 갯수를 정합니다(10개 이하로 정의)
        for (MultipartFile multipartFile : multipartFiles) {
            if (fileUrls.size() > 10) {
                throw new FileException(BaseExceptionCode.FILE_UPLOAD_EXCEEDED);
            }

            FileUpload fileUpload = convertFile(multipartFile);
            String storeFileName = fileUpload.getStoreFileName();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {
                s3.putObject(new PutObjectRequest(bucketName, storeFileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                fileUrls.add(s3.getUrl(bucketName, storeFileName).toString());
            } catch (IOException e) {
                throw new FileException(BaseExceptionCode.FILE_UPLOAD_FAILED, e);
            }
        }

        return fileUrls;
    }

    public void validateFileExistsAtUrl(String resourcePath) {

        if (!s3.doesObjectExist(bucketName, resourcePath)) {
            throw new NotFoundException(BaseExceptionCode.FILE_NOT_FOUND);
        }
    }


    public void test() {
        // list all in the bucket
        try {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withMaxKeys(300);

            ObjectListing objectListing = s3.listObjects(listObjectsRequest);

            System.out.println("Object List:");
            while (true) {
                for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {

                    System.out.println("    name=" + objectSummary.getKey() + ", size=" + objectSummary.getSize() + ", owner=" + objectSummary.getOwner().getId());
                }

                if (objectListing.isTruncated()) {
                    objectListing = s3.listNextBatchOfObjects(objectListing);
                } else {
                    break;
                }
            }
        } catch (AmazonS3Exception e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }

        // top level folders and files in the bucket
        try {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withDelimiter("/")
                    .withMaxKeys(300);

            ObjectListing objectListing = s3.listObjects(listObjectsRequest);

            System.out.println("Folder List:");
            for (String commonPrefixes : objectListing.getCommonPrefixes()) {
                System.out.println("    name=" + commonPrefixes);
            }

            System.out.println("File List:");
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println("    name=" + objectSummary.getKey() + ", size=" + objectSummary.getSize() + ", owner=" + objectSummary.getOwner().getId());
            }
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch(SdkClientException e) {
            e.printStackTrace();
        }
    }
}
