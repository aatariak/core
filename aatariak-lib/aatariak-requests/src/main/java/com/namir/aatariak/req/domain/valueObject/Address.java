package com.namir.aatariak.req.domain.valueObject;

import com.namir.aatariak.shared.libs.ValueObjectBaseInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address implements ValueObjectBaseInterface {
    private String city;
    private String street;
    private String building;
    private Integer floor;
}
