package com.namir.aatariak.storage.application.dto;

import com.namir.aatariak.shared.libs.DtoInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UploadMultipartDTO implements DtoInterface {
    private String dir;
    private MultipartFile file;
    private String fullPath;

    public UploadMultipartDTO(MultipartFile file) {
        this.dir = "uploads";
        this.file = file;

        String storeImageName = UUID.randomUUID() + "_" + file.getName();
        this.fullPath = String.format("%s/%s", this.getDir(), storeImageName);

    }

    public UploadMultipartDTO(String dir, MultipartFile file) {
        this.dir = dir;
        this.file = file;

        String storeImageName = UUID.randomUUID() + "_" + file.getName();
        this.fullPath = String.format("%s/%s", this.getDir(), storeImageName);
    }
}
