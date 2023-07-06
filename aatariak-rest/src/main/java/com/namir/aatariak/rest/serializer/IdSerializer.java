package com.namir.aatariak.rest.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.namir.aatariak.shared.valueObjects.ID;

import java.io.IOException;

public class IdSerializer extends JsonSerializer<ID> {
    @Override
    public void serialize(ID id, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(id.toString());
    }
}
