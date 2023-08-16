package com.namir.aatariak.req.infrastructure.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.namir.aatariak.req.domain.valueObject.Address;
import com.namir.aatariak.req.domain.valueObject.IdentificationImage;
import com.namir.aatariak.req.domain.valueObject.IdentificationImageList;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.List;

@ReadingConverter
public class IdPapersReadingConverter implements Converter<PGobject, IdentificationImageList> {

    @Override
    public IdentificationImageList convert(PGobject source) {
        ObjectMapper objectMapper = new ObjectMapper();
        String value = source.getValue();
        try {
            return objectMapper.readValue(value, IdentificationImageList.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
