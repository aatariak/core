package com.namir.aatariak.req.domain.valueObject;

import lombok.Data;
import lombok.AllArgsConstructor;
import com.namir.aatariak.shared.libs.ValueObjectBaseInterface;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentificationImage implements ValueObjectBaseInterface {
    private String type;
    private String imagePath;
}
