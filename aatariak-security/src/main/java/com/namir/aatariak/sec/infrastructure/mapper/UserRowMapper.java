package com.namir.aatariak.sec.infrastructure.mapper;

import com.namir.aatariak.sec.domain.entity.User;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import com.namir.aatariak.shared.valueObjects.ID;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(new ID(rs.getString("id")));
        user.setName(rs.getString("name"));
        user.setEmail(new EmailAddress(rs.getString("email")));
        user.setPassword(rs.getString("password"));
        user.setEnabled(rs.getBoolean("enabled"));
        return user;
    }
}
