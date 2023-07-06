package com.namir.aatariak.sec.user.user;


import com.namir.aatariak.containerTestIntegration;
import com.namir.aatariak.sec.user.user.database.repository.UserRepository;
import com.namir.aatariak.sec.user.user.database.repository.UserRepositoryImpl;
import com.namir.aatariak.sec.user.user.domain.entity.User;
import com.namir.aatariak.sec.user.user.domain.valueObject.EmailAddress;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

import static com.namir.aatariak.sec.user.user.domain.entity.UserAuthority.USER_ROLE;


public class DatabaseUserTests extends containerTestIntegration {

    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DatabaseUserTests() {
        userRepository = new UserRepositoryImpl(testJdbcTemplate);
    }

    @Test
    public void testUserInsertionWithRelation() {
        User user = new User();
        user.setEmail(new EmailAddress("faridMousa@gmail.com"));
        user.setName("mousa");
        user.setPassword("passwordtobehashedlater");
        user.addAuthority(USER_ROLE);

        User savedUser = this.userRepository.createUser(user);
        System.out.println(savedUser);
    }

    @Test
    public void testUserAuthentication() {

        User user = new User();
        user.setEmail(new EmailAddress("namirabboud@gmail.com"));
        user.setName("mousa");
        user.setPassword(this.passwordEncoder.encode("namir"));
        user.addAuthority(USER_ROLE);

        User savedUser = this.userRepository.createUser(user);

        RestTemplate restTemplate = new RestTemplate();

        Object[] testResponse = restTemplate.exchange("http://localhost:8080/api/test", HttpMethod.GET, new HttpEntity<>(createHeaders("namirabboud@gmail.com", "namir")), Object[].class).getBody();
        System.out.println(testResponse);
    }

    private HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}
