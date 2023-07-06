package com.namir.aatariak.sec.user.user.database.mapper;

import com.namir.aatariak.shared.domain.valueObjects.ID;
import com.namir.aatariak.sec.user.user.domain.entity.User;
import com.namir.aatariak.sec.user.user.domain.valueObject.EmailAddress;
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
        return user;
    }
}
