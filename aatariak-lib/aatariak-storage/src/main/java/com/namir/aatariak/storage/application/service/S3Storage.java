package com.namir.aatariak.storage.application.service;

import com.namir.aatariak.storage.application.dto.UploadMultipartDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class S3Storage {
    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.endpoint-url}")
    private String endpointUrl;

    @Value("${aws.bucket-name}")
    private String bucketName;

    private S3AsyncClient s3;

    public S3Storage() {
    }

    @PostConstruct
    public void init() {
        this.s3 = S3AsyncClient.builder()
                .endpointOverride(URI.create(endpointUrl))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

//    public StorageImage upload(StorageImage image) {
//        PutObjectRequest request = PutObjectRequest
//                .builder()
//                .contentType(image.getContentType())
//                .bucket(bucketName)
//                .key(String.format("%s/%s", image.getDir(), image.getImageName()))
//                .build();
//
//        CompletableFuture<PutObjectResponse> cf =  s3.putObject(request, AsyncRequestBody.fromFile(new File(image.getImagePath())));
//        try {
//            System.out.println(cf.get().eTag());
//            return image;
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public String uploadMultipart(UploadMultipartDTO image) throws ExecutionException, InterruptedException, IOException {

        String imageKey = image.getFullPath();

        CreateMultipartUploadRequest request = CreateMultipartUploadRequest
                .builder()
                .bucket(bucketName)
                .key(imageKey)
                .build();

        CompletableFuture<CreateMultipartUploadResponse> createResponse = s3.createMultipartUpload(request);

        String uploadId = createResponse.get().uploadId();

        // Prepare the parts to be uploaded
        List<CompletedPart> completedParts = new ArrayList<>();
        int partNumber = 1;
        ByteBuffer buffer = ByteBuffer.allocate(5 * 1024 * 1024); // Set your preferred part size (5 MB in this example)

        // Read the file and upload each part
        File file = this.convertMultipartFileToFile(image.getFile());
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file.getPath(), "r")) {
            long fileSize = file.length();
            long position = 0;

            while (position < fileSize) {
                randomAccessFile.seek(position);
                int bytesRead = randomAccessFile.getChannel().read(buffer);

                buffer.flip();
                UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                        .bucket(bucketName)
                        .key(imageKey)
                        .uploadId(uploadId)
                        .partNumber(partNumber)
                        .contentLength((long) bytesRead)
                        .build();

                CompletableFuture<UploadPartResponse> response = s3.uploadPart(uploadPartRequest, AsyncRequestBody.fromByteBuffer(buffer));

                completedParts.add(CompletedPart.builder()
                        .partNumber(partNumber)
                        .eTag(response.get().eTag())
                        .build());

                buffer.clear();
                position += bytesRead;
                partNumber++;
            }
            // Complete the multipart upload
            CompletedMultipartUpload completedUpload = CompletedMultipartUpload.builder()
                    .parts(completedParts)
                    .build();

            CompleteMultipartUploadRequest completeRequest = CompleteMultipartUploadRequest.builder()
                    .bucket(bucketName)
                    .key(imageKey)
                    .uploadId(uploadId)
                    .multipartUpload(completedUpload)
                    .build();

            CompletableFuture<CompleteMultipartUploadResponse> completeResponse = s3.completeMultipartUpload(completeRequest);

            return image.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("temp", null); // Create a temporary file
        file.deleteOnExit(); // Delete the file when the JVM exits

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes()); // Copy the content of MultipartFile to the file
        }

        return file;
    }
}
