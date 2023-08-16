package com.namir.aatariak.rest.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.namir.aatariak.user.domain.entity.Role;

import java.io.IOException;

public class RoleSerializer extends JsonSerializer<Role> {
    @Override
    public void serialize(Role role, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(role.toString());
    }
}
