package com.sparta.insuranceclaim;

import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class InsuranceclaimApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceclaimApplication.class, args);
	}

	 // Code to create test accounts. RUN ONCE ONLY!
//	@Bean
//	CommandLineRunner runner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//		return args -> {
//			userRepository.save(new User("user", passwordEncoder.encode("password"), "ROLE_USER"));
//			userRepository.save(new User("agent", passwordEncoder.encode("password"), "ROLE_AGENT"));
//			userRepository.save(new User("admin", passwordEncoder.encode("password"), "ROLE_AGENT,ROLE_ADMIN"));
//		};
//	}

}
