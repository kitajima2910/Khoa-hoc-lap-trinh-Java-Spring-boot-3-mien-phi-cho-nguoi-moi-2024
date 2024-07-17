package com.pxh2910.identity_service.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.pxh2910.identity_service.dto.request.AuthenticationRequest;
import com.pxh2910.identity_service.dto.request.IntrospectRequest;
import com.pxh2910.identity_service.dto.response.AuthenticationResponse;
import com.pxh2910.identity_service.dto.response.IntrospectResponse;
import com.pxh2910.identity_service.entity.User;
import com.pxh2910.identity_service.exception.AppException;
import com.pxh2910.identity_service.exception.ErrorCode;
import com.pxh2910.identity_service.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

	UserRepository userRepository;

	@NonFinal
	@Value("${jwt.signerKey}")
	protected String SIGNER_KEY;

	public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {

		var token = request.getToken();

		JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

		SignedJWT signedJWT = SignedJWT.parse(token);

		Date exprityTime = signedJWT.getJWTClaimsSet().getExpirationTime();

		var verfied = signedJWT.verify(jwsVerifier);

		return IntrospectResponse.builder().valid(verfied && exprityTime.after(new Date())).build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		var user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

		if (!authenticated) {
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		}

		var token = generateToken(user);

		return AuthenticationResponse.builder().token(token).authenticated(authenticated).build();
	}

	private String generateToken(User user) {

		JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

		JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
				.subject(user.getUsername()).issuer("pxh2910")
				.issueTime(new Date())
				.expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
				.claim("scope", buildScope(user))
				.build();

		Payload payload = new Payload(jwtClaimsSet.toJSONObject());

		JWSObject jwsObject = new JWSObject(jwsHeader, payload);

		try {
			jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jwsObject.serialize();
	}
	
	private String buildScope(User user) {
		StringJoiner stringJoiner = new StringJoiner(" ");
		if (!CollectionUtils.isEmpty(user.getRoles())) {
			user.getRoles().forEach(s -> stringJoiner.add(s));
		}
		
		return stringJoiner.toString();
	}

}
