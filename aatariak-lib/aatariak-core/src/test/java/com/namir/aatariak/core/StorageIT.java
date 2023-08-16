package com.namir.aatariak.core;

import com.namir.aatariak.storage.AatariakStorageApplication;
import com.namir.aatariak.storage.application.dto.UploadMultipartDTO;
import com.namir.aatariak.storage.application.service.S3Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.concurrent.ExecutionException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AatariakStorageApplication.class, TestConfiguration.class})
public class StorageIT {

    @Autowired
    private S3Storage fileStore;

    @Test
    public void testStorageMultipartUpload() throws IOException, ExecutionException, InterruptedException {
        File imageFile = new File(DriverRequestIT.class.getClassLoader().getResource("images/turtle.png").getFile()); // Replace with the actual path to your image file
        InputStream inputStream = new FileInputStream(imageFile);
        MultipartFile file = new MockMultipartFile("turtle.png", "turtle.png", "image/png", inputStream);
        String path = fileStore.uploadMultipart(new UploadMultipartDTO(file));
        Assertions.assertNotNull(path);
    }
}
