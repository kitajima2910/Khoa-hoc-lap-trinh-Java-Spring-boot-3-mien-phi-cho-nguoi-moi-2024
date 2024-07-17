package com.pxh2910.identity_service.configuration;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pxh2910.identity_service.entity.User;
import com.pxh2910.identity_service.enums.Role;
import com.pxh2910.identity_service.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Configuration
public class ApplicationInitConfig {
	
	PasswordEncoder passwordEncoder;

	@Bean
	ApplicationRunner applicationRunner(UserRepository userRepository) {
		return args -> {
			if (userRepository.findByUsername("admin").isEmpty()) {
				var roles = new HashSet<String>();
				roles.add(Role.ADMIN.name());
				
				User user = User.builder()
						.username("admin")
						.password(passwordEncoder.encode("admin"))
						.roles(roles)
						.build();
				
				userRepository.save(user);
				log.warn("Admin user has bean created with default password: admin, please change it");
			}
		};
	}
	
}
