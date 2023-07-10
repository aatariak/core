package com.namir.aatariak.rest.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizeRequest {
    private String email;
    private String password;
}
