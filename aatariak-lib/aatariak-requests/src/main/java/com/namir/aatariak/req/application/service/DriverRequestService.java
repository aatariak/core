package com.namir.aatariak.req.application.service;

import com.namir.aatariak.req.application.dto.ApplyForRequestDTO;
import com.namir.aatariak.req.domain.entity.DriverRequest;

public interface DriverRequestService {
    DriverRequest create(ApplyForRequestDTO request);
}
