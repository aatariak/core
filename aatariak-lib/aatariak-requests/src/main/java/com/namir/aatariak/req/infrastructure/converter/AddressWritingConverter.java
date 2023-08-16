package com.namir.aatariak.req.infrastructure.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.namir.aatariak.req.domain.valueObject.Address;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.sql.SQLException;

@WritingConverter
public class AddressWritingConverter implements Converter<Address, PGobject> {
    @Override
    public PGobject convert(Address source) {
        ObjectMapper objectMapper = new ObjectMapper();

        PGobject jsonObject = new PGobject();
        jsonObject.setType("json");
        try {
            System.out.println(objectMapper.writeValueAsString(source));
            jsonObject.setValue(objectMapper.writeValueAsString(source));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}