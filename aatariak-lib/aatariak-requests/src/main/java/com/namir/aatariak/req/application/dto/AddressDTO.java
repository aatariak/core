package com.namir.aatariak.req.application.dto;

import com.namir.aatariak.shared.libs.DtoInterface;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDTO implements DtoInterface {
    private String city;
    private String street;
    private String building;
    private Integer floor;
}
