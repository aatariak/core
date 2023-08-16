package com.namir.aatariak.req.infrastructure.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.namir.aatariak.req.domain.entity.DriverRequest;
import com.namir.aatariak.req.domain.valueObject.Address;
import com.namir.aatariak.req.domain.valueObject.IdentificationImage;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import com.namir.aatariak.shared.valueObjects.ID;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DriverRequestRowMapper implements RowMapper<DriverRequest> {
    @Override
    public DriverRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper();

        DriverRequest request = new DriverRequest();
        request.setId(UUID.fromString(rs.getString("id")));
        request.setFirstName(rs.getString("first_name"));
        request.setLastName(rs.getString("last_name"));
        request.setEmail(new EmailAddress(rs.getString("email")));
        try {
            request.setAddress(objectMapper.readValue(rs.getObject("address").toString(), Address.class));
            request.setIdPapers(objectMapper.readValue(rs.getString("id_papers"), new TypeReference<>() {}));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return request;
    }
}
