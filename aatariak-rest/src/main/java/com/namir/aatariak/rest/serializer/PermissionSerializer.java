package com.namir.aatariak.rest.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.namir.aatariak.sec.domain.valueObject.Permission;

import java.io.IOException;

public class PermissionSerializer extends JsonSerializer<Permission> {

    @Override
    public void serialize(Permission permission, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(permission.toString());
    }
}
