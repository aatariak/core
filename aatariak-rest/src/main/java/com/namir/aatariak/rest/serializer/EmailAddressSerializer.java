package com.namir.aatariak.rest.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.namir.aatariak.shared.valueObjects.EmailAddress;

import java.io.IOException;

public class EmailAddressSerializer extends JsonSerializer<EmailAddress> {
    @Override
    public void serialize(EmailAddress emailAddress, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(emailAddress.toString());
    }
}
