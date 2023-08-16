package com.namir.aatariak.rest.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.namir.aatariak.req.application.dto.ApplyForRequestDTO;
import com.namir.aatariak.req.application.service.DriverRequestService;

public class RequestsController extends BaseController{
    private final DriverRequestService driverRequestService;

    public RequestsController(DriverRequestService driverRequestService) {
        this.driverRequestService = driverRequestService;
    }

    @PostMapping("/requests")
    public ResponseEntity<String> create(@Valid @RequestBody ApplyForRequestDTO request) {
        this.driverRequestService.create(request);
        return ResponseEntity.status(HttpStatus.OK).body("Request Created. You will be contacted soon. Ntebeh la halak bro");
    }

}
