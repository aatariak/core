package com.namir.aatariak.aatariakauthserver.infrastructure.repository;

import com.namir.aatariak.aatariakauthserver.domain.entity.Role;
import com.namir.aatariak.aatariakauthserver.domain.entity.User;
import com.namir.aatariak.aatariakauthserver.infrastructure.mapper.UserRowMapper;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private PasswordEncoder passwordEncoder;

    public final static RowMapper<User> userMapper = new UserRowMapper();

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User find(String id) {
        return jdbcTemplate.query("SELECT * FROM users u LEFT JOIN roles r " +
                "ON u.id = r.user_id WHERE u.id = ?", this.userWithRoleExtractor(), UUID.fromString(id));
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

    @Override
    public Long count() {
        Long count =  jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Long.class);
        return count != null ? count : 0;
    }

    @Override
    public User findByUsernameAndEnabledIsTrue(EmailAddress emailAddress) {
        String email = emailAddress.toString();
        try {
            return jdbcTemplate.query("SELECT * FROM users u LEFT JOIN roles a " +
                    "ON u.id = a.user_id WHERE u.email = ?", this.userWithRoleExtractor(), email);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM users u LEFT JOIN roles a ON u.id = a.user_id", this.userListWithRoleExtractor());

    }

    private ResultSetExtractor<User> userWithRoleExtractor() {
        return new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet rs) throws SQLException, DataAccessException {
                User user = null;
                while (rs.next()) {
                    if (user == null) {
                        user = userMapper.mapRow(rs, 0);
                    }
                    user.addRole(rs.getString("permission"));
                }
                return user;
            }
        };
    }

    private ResultSetExtractor<List<User>> userListWithRoleExtractor() {
        return new ResultSetExtractor<List<User>>() {
            @Override
            public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<User> list = new ArrayList<>();
                while (rs.next()) {
                    User user = userMapper.mapRow(rs, 0);
                    user.addRole(rs.getString("permission"));
                    list.add(user);
                }

                return list;
            }
        };
    }

}
