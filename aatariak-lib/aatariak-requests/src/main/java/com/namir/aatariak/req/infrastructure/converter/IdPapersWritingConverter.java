package com.namir.aatariak.req.infrastructure.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.namir.aatariak.req.domain.valueObject.IdentificationImageList;
import org.postgresql.util.PGobject;
import org.springframework.data.convert.WritingConverter;
import org.springframework.core.convert.converter.Converter;
import com.namir.aatariak.req.domain.valueObject.IdentificationImage;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WritingConverter
public class IdPapersWritingConverter implements Converter<IdentificationImageList, PGobject> {
    @Override
    public PGobject convert(IdentificationImageList source) {
        ObjectMapper objectMapper = new ObjectMapper();

        PGobject jsonObject = new PGobject();
        jsonObject.setType("json");

        try {
            jsonObject.setValue(objectMapper.writeValueAsString(source));
            System.out.println(objectMapper.writeValueAsString(source));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
