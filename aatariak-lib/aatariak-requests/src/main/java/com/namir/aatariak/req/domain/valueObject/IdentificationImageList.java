package com.namir.aatariak.req.domain.valueObject;

import com.namir.aatariak.shared.libs.ValueObjectBaseInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentificationImageList implements ValueObjectBaseInterface {
    private List<IdentificationImage> images;
}
