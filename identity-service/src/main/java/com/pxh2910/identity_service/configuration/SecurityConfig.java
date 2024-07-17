package com.pxh2910.identity_service.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.pxh2910.identity_service.enums.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	public final String[] PUCLIC_ENDPOINT = { "/users", "/auth/token", "/auth/introspect" };

	@Value("${jwt.signerKey}")
	private String signerKey;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(request -> request
				.requestMatchers(HttpMethod.POST, PUCLIC_ENDPOINT)
				.permitAll().requestMatchers(HttpMethod.GET, "/users").hasRole(Role.ADMIN.name()).anyRequest()
				.authenticated());

		httpSecurity.oauth2ResourceServer(oauth2 -> {
			oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
					.jwtAuthenticationConverter(jwtAuthenticationConverter()));
		});

		httpSecurity.csrf(AbstractHttpConfigurer::disable);

		return httpSecurity.build();
	}

	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

		return converter;
	}

	@Bean
	JwtDecoder jwtDecoder() {

		SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");

		return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

}
