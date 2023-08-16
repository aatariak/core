package com.namir.aatariak.core;

import com.namir.aatariak.req.application.dto.AddressDTO;
import com.namir.aatariak.req.application.dto.ApplyForRequestDTO;
import com.namir.aatariak.req.application.dto.IdentificationPapersDTO;
import com.namir.aatariak.req.domain.entity.DriverRequest;
import com.namir.aatariak.storage.AatariakStorageApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.namir.aatariak.req.AatariakReqApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.namir.aatariak.req.application.service.DriverRequestService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AatariakReqApplication.class, AatariakStorageApplication.class})
public class DriverRequestIT extends BaseIntegrationClass{

    @Autowired
    private DriverRequestService driverRequestService;

    @Test
    public void testRequestInsertion() throws IOException {

        // Mocking the multipartFile from existing image
        File imageFile = new File(DriverRequestIT.class.getClassLoader().getResource("images/turtle.png").getFile()); // Replace with the actual path to your image file
        InputStream inputStream = new FileInputStream(imageFile);
        MultipartFile file = new MockMultipartFile("turtle.png", "turtle.png", "image/png", inputStream);

        List<IdentificationPapersDTO> imagesList = new ArrayList<>();
        IdentificationPapersDTO image = new IdentificationPapersDTO("profile-picture", file);
        IdentificationPapersDTO image2 = new IdentificationPapersDTO("driving-license", file);
        imagesList.add(image);
        imagesList.add(image2);

        // Create the DTO
        ApplyForRequestDTO request = new ApplyForRequestDTO();
        request.setFirstName("Farid");
        request.setLastName("Mousa");
        request.setEmail("namirabboud@gmail.com");
        request.setAddress(new AddressDTO("Lille", "rue de trevise", "montesquieu", 315));
        request.setIdentificationImages(imagesList);

        DriverRequest savedRequest = this.driverRequestService.create(request);
        Assertions.assertNotNull(savedRequest);
    }
}
