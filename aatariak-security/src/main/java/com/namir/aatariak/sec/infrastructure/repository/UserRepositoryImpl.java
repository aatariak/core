package com.namir.aatariak.sec.infrastructure.repository;

import com.namir.aatariak.sec.domain.entity.Role;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import com.namir.aatariak.sec.domain.entity.User;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import org.springframework.dao.EmptyResultDataAccessException;
import com.namir.aatariak.sec.infrastructure.mapper.UserRowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    private PasswordEncoder passwordEncoder;

    public final static RowMapper<User> userMapper = new UserRowMapper();

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User find(String id) {
        String sql = "SELECT u.*, string_agg(a.permission, ',') as permissions from users u left join roles a ON u.id = a.user_id where u.id = ? group by u.id;";

        return jdbcTemplate.query(sql, this.userWithRoleExtractor(), UUID.fromString(id));
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

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT u.*, string_agg(a.permission, ',') as permissions from users u left join roles a ON u.id = a.user_id group by u.id;";
        return jdbcTemplate.query(sql, this.userListWithRoleExtractor());
    }

    @Override
    public Long count() {
        Long count =  jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Long.class);
        return count != null ? count : 0;
    }

    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("insert into users (name, email, password) values (?, ?, ?)", new String[]{"id"});
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail().toString());
                ps.setString(3, passwordEncoder.encode(user.getPassword()));
                return ps;
            }
        }, keyHolder);

        Object userId = keyHolder.getKeyList().get(0).get("id");
        Iterator<Role> roleListIterator = user.getRoles().iterator();

        while (roleListIterator.hasNext()) {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement("insert into roles (user_id, permission) values (?, ?)");
                    ps.setObject(1, userId);
                    ps.setString(2, roleListIterator.next().getPermission().toString());
                    return ps;
                }
            });
        }

        return this.find(userId.toString());
    }

    private ResultSetExtractor<User> userWithRoleExtractor() {
        return rs -> {
            User user = null;
            while (rs.next()) {
                if (user == null) {
                    user = userMapper.mapRow(rs, 0);
                }
                String permissions = rs.getString("permissions");
                String[] permissionArray = permissions.split(",");
                for (String permission: permissionArray) {
                    user.addRole(permission);
                }
            }
            return user;
        };
    }

    private ResultSetExtractor<List<User>> userListWithRoleExtractor() {
        return new ResultSetExtractor<List<User>>() {
            @Override
            public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<User> list = new ArrayList<>();
                while (rs.next()) {
                    User user = userMapper.mapRow(rs, 0);
                    String permissions = rs.getString("permissions");
                    String[] permissionArray = permissions.split(",");
                    for (String permission: permissionArray) {
                        user.addRole(permission);
                    }
                    list.add(user);
                }

                return list;
            }
        };
    }

}
