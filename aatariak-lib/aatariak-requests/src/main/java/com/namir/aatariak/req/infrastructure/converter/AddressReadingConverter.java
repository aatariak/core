package com.namir.aatariak.req.infrastructure.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.namir.aatariak.req.domain.valueObject.Address;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class AddressReadingConverter implements Converter<PGobject, Address> {
    @Override
    public Address convert(PGobject source) {
        ObjectMapper objectMapper = new ObjectMapper();
        String value = source.getValue();
        try {
            return objectMapper.readValue(value, Address.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
