package in.coronainfo.server.test;


import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BucketTest {
    public static void uploadObject(String projectId, String bucketName, String objectName, String filePath) throws IOException {
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

        System.out.println("File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
    }

    public static void main(String[] args) {
        try {
            // The ID of your GCP project
            String projectId = "corona-dashboard-270916";

            // The ID of your GCS bucket
            String bucketName = "corona-dashboard-bucket";

            // The ID of your GCS object
            String objectName = "test/test-object-name1";

            // The path to your file to upload
            String filePath = "F:\\GoogleDrive\\coronaDashboard\\backup\\summaryGlobal.json";

            uploadObject(projectId, bucketName, objectName, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}