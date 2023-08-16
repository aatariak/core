package com.namir.aatariak.sec.context.infrastructure.repository;

import com.namir.aatariak.sec.context.domain.entity.User;
import com.namir.aatariak.sec.context.infrastructure.mapper.UserRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.*;

@Repository("secUserRepository")
public class SecUserRepositoryImpl implements SecUserRepository {

    private final JdbcTemplate jdbcTemplate;

    private PasswordEncoder passwordEncoder;

    public final static RowMapper<User> userMapper = new UserRowMapper();

    public SecUserRepositoryImpl(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User findByUsernameAndEnabledIsTrue(EmailAddress emailAddress) {
        String email = emailAddress.toString();
        String sql = "SELECT u.*, string_agg(a.permission, ',') as permissions from users u left join roles a ON u.id = a.user_id  where u.email = ? group by u.id;";
        try {
            return jdbcTemplate.query(sql, this.userWithRoleExtractor(), email);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    private ResultSetExtractor<User> userWithRoleExtractor() {
        return rs -> {
            User user = null;
            while (rs.next()) {
                user = userMapper.mapRow(rs, 0);
            }
            return user;
        };
    }

    private ResultSetExtractor<List<User>> userListWithRoleExtractor() {
        return rs -> {
            List<User> list = new ArrayList<>();
            while (rs.next()) {
                User user = userMapper.mapRow(rs, 0);
                list.add(user);
            }

            return list;
        };
    }

}
