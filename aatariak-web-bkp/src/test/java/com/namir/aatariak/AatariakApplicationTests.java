package com.namir.aatariak;

import com.namir.aatariak.shared.application.DTO.RegisterDto;
import com.namir.aatariak.sec.user.user.database.repository.UserRepository;
import com.namir.aatariak.sec.user.user.database.service.UserService;
import com.namir.aatariak.sec.user.user.domain.entity.User;
import com.namir.aatariak.sec.user.user.domain.entity.UserAuthority;
import com.namir.aatariak.sec.user.user.domain.valueObject.EmailAddress;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.RestTemplate;


import static com.namir.aatariak.sec.user.user.domain.entity.UserAuthority.USER_ROLE;

@SpringBootTest
class AatariakApplicationTests {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {
	}

	@Test
	void testUserService() throws MethodArgumentNotValidException {
		User user = new User();
		user.setEmail(new EmailAddress("namirabboud@gmail.com"));
		user.setName("mousa");
		user.setPassword(this.passwordEncoder.encode("namir"));
		user.addAuthority(USER_ROLE);

		User savedUser = this.userRepository.createUser(user);
		System.out.println("User id" + savedUser.getId());
	}

	@Test
	void testControllerResponse() {

		JSONObject user = new JSONObject();
		user.put("name", "farid");
		user.put("email", "faridMousaaa@gmail.com");
		user.put("password", "cousacousa");

		RestTemplate restTemplate = new RestTemplate();

		user = restTemplate.postForObject("http://localhost:8080/api/register", user, JSONObject.class);

		System.out.println(user);
	}

	@Test
	void testUserAuthoritiesRelation() {
		User user = new User();
		user.setEmail(new EmailAddress("faridMousa@gmail.com"));
		user.setName("mousa");
		user.setPassword("passwordtobehashedlater");
		user.addAuthority(USER_ROLE);

		// Authorities are not something a user can add, it's defined by us, we can add as much as we want and then reference it from add
		UserAuthority authority = user.getUserAuthorities().get(0);
		Assertions.assertEquals(USER_ROLE, authority.getAuthority());

	}
}
