package com.namir.aatariak.user.infrastructure.repository;

import com.namir.aatariak.shared.valueObjects.EmailAddress;
import com.namir.aatariak.user.domain.entity.Role;
import com.namir.aatariak.user.domain.entity.User;
import com.namir.aatariak.user.infrastructure.mapper.UserRowMapper;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private final PasswordEncoder passwordEncoder;

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

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into users (name, email, password) values (?, ?, ?)", new String[]{"id"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail().toString());
            ps.setString(3, passwordEncoder.encode(user.getPassword()));
            return ps;
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
                user = userMapper.mapRow(rs, 0);
            }
            return user;
        };
    }

    /*
    In order to fetch the data using ResultSetExtractor,
    we need to implement the ResultSetExtractor interface and provide the definition for its method.
    It has only one method. i.e., extractData() which takes an instance of ResultSet as an argument and returns the list.
    Below we are using lambda
     */
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
