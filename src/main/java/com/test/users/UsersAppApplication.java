package com.test.users;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.javafaker.Faker;
import com.test.users.entities.Role;
import com.test.users.entities.User;
import com.test.users.entities.UserInRole;
import com.test.users.repositories.RoleRepository;
import com.test.users.repositories.UserInRoleRepository;
import com.test.users.repositories.UserRepository;

@SpringBootApplication
public class UsersAppApplication implements ApplicationRunner {

	@Autowired
	private Faker faker;

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserInRoleRepository userInRoleRepository;

	private static final Logger log = LoggerFactory.getLogger(UsersAppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UsersAppApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Role roles[] = { new Role("ADMIN"), new Role("SUPPORT"), new Role("USER") };

		for (Role role : roles) {
			roleRepository.save(role);
		}

		for (int i = 0; i < 1000; i++) {
			User user = new User();
			user.setUsername(faker.name().username());
			user.setPassword(faker.dragonBall().character());
			User created = repository.save(user);
			UserInRole userInRole = new UserInRole(created, roles[new Random().nextInt(3)]);
			log.info("USer created username {} password {} role {}", created.getUsername(), created.getPassword(),
					userInRole.getRole().getName());
			userInRoleRepository.save(userInRole);
		}

	}

}
