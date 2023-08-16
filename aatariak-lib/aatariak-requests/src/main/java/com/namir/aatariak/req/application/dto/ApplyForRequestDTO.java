package com.namir.aatariak.req.application.dto;

import com.namir.aatariak.shared.libs.DtoInterface;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApplyForRequestDTO implements DtoInterface {
    private String firstName;
    private String lastName;
    private String email;
    private String routes;
    private AddressDTO address;
    private List<IdentificationPapersDTO> identificationImages;

    public ApplyForRequestDTO() {}
}
