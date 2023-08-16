package com.namir.aatariak.req.application.dto;

import com.namir.aatariak.shared.libs.DtoInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class IdentificationPapersDTO implements DtoInterface {
    private String type;
    private MultipartFile image;
}
