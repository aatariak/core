package com.namir.aatariak.req.application.service;

import com.namir.aatariak.req.domain.valueObject.IdentificationImageList;
import org.springframework.stereotype.Service;
import com.namir.aatariak.req.domain.valueObject.Address;
import com.namir.aatariak.req.domain.entity.DriverRequest;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import com.namir.aatariak.storage.application.service.S3Storage;
import com.namir.aatariak.req.application.dto.ApplyForRequestDTO;
import com.namir.aatariak.req.domain.valueObject.IdentificationImage;
import com.namir.aatariak.storage.application.dto.UploadMultipartDTO;
import com.namir.aatariak.req.application.dto.IdentificationPapersDTO;
import com.namir.aatariak.req.infrastructure.repository.DriverRequestRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Service
public class DriverRequestServiceImpl implements DriverRequestService{

    private final DriverRequestRepository driverRequestRepository;

    private final S3Storage fileStore;

    public DriverRequestServiceImpl(
            DriverRequestRepository driverRequestRepository,
            S3Storage fileStore
    ) {
        this.driverRequestRepository = driverRequestRepository;
        this.fileStore = fileStore;
    }

    @Override
    public DriverRequest create(ApplyForRequestDTO request) {
        List<IdentificationImage> idPapers = new ArrayList<>();
        for(IdentificationPapersDTO image:request.getIdentificationImages()) {
            // store the image
            try {
                String path = fileStore.uploadMultipart(new UploadMultipartDTO(image.getImage()));
                idPapers.add(new IdentificationImage(image.getType(), path));
            } catch (ExecutionException | InterruptedException | IOException exception) {
                exception.printStackTrace();
            }
        }

        Address address = new Address(request.getAddress().getCity(), request.getAddress().getStreet(), request.getAddress().getBuilding(), request.getAddress().getFloor());

        DriverRequest driverRequest = new DriverRequest();
        driverRequest.setFirstName(request.getFirstName());
        driverRequest.setLastName(request.getLastName());
        driverRequest.setEmail(new EmailAddress(request.getEmail()));
        driverRequest.setAddress(address);
        driverRequest.setIdPapers(new IdentificationImageList(idPapers));

        return this.driverRequestRepository.save(driverRequest);
    }
}
