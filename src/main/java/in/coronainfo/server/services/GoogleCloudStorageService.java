package in.coronainfo.server.services;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Log4j2
@RequiredArgsConstructor
public class GoogleCloudStorageService {

    @Value("${gcp.projectId}")
    private String gcpProjectId;    // gcp project id

    @Value("${gcp.gcs.bucket.id}")
    private String bucketName; // id of GCS bucket

    public void uploadFile(String fileName, String objectName) {
        log.info("Going to upload file to Google Bucket. bucketName:{}, fileName:{}, objectName:{}", bucketName,
                fileName, objectName);
        try {
            Storage storage = StorageOptions.newBuilder().setProjectId(gcpProjectId).build().getService();
            BlobId blobId = BlobId.of(bucketName, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, Files.readAllBytes(Paths.get(fileName)));

            log.info("Successfully uploaded file to Google Bucket. bucketName:{}, fileName:{}, objectName:{}",
                    bucketName,
                    fileName, objectName);
        } catch (Exception e) {
            log.error("Exception occurred while uploading file to Google Bucket. bucketName:{}, fileName:{}, " +
                            "objectName:{}", bucketName,
                    fileName, objectName, e);
        }
    }

}