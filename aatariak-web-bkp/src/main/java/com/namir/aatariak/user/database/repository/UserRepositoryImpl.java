package com.namir.aatariak.sec.user.user.database.repository;

import com.namir.aatariak.sec.user.user.database.mapper.UserRowMapper;
import com.namir.aatariak.sec.user.user.domain.entity.User;
import com.namir.aatariak.sec.user.user.domain.entity.UserAuthority;
import com.namir.aatariak.sec.user.user.domain.valueObject.EmailAddress;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.UUID;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public final static RowMapper<User> userMapper = new UserRowMapper();

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("insert into users (name, email, password) values (?, ?, ?)", new String[]{"id"});
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());
                return ps;
            }
        }, keyHolder);

        Object userId = keyHolder.getKeyList().get(0).get("id");
        ListIterator<UserAuthority> authoritiesListIterator = user.getUserAuthorities().listIterator();

        while (authoritiesListIterator.hasNext()) {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement("insert into user_authorities (user_id, authority) values (?, ?)");
                    ps.setObject(1, userId);
                    ps.setString(2, authoritiesListIterator.next().getAuthority());
                    return ps;
                }
            });
        }
        return getUser(userId.toString());
    }

    @Override
    public User getUser(String id) {
//        User user = jdbcTemplate.queryForObject("select * from users where id = ?", new UserRowMapper(), UUID.fromString(id));
//        return user;

        return jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_authorities a " +
                        "ON u.id = a.user_id WHERE u.id = ?", this.userWithAuthorityExtractor(), UUID.fromString(id));
    }

    @Override
    public User getUserByEmail(EmailAddress emailAddress) {
        String email = emailAddress.toString();
        try {
            return jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_authorities a " +
                            "ON u.id = a.user_id WHERE u.email = ?", this.userWithAuthorityExtractor(), email);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public Long count() {
        Long count =  jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Long.class);
        return count != null ? count : 0;
    }

    private ResultSetExtractor<User> userWithAuthorityExtractor() {
        return new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet rs) throws SQLException, DataAccessException {
                User user = null;
                while (rs.next()) {
                    if (user == null) {
                        user = userMapper.mapRow(rs, 0);
                    }
                    user.addAuthority(rs.getString("authority"));
                }
                return user;
            }
        };
    }
}
