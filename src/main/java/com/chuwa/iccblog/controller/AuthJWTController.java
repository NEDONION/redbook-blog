package com.chuwa.iccblog.controller;

import com.chuwa.iccblog.dao.security.RoleRepository;
import com.chuwa.iccblog.dao.security.UserRepository;
import com.chuwa.iccblog.entity.security.Role;
import com.chuwa.iccblog.entity.security.User;
import com.chuwa.iccblog.payload.security.JWTAuthResponse;
import com.chuwa.iccblog.payload.security.LoginDto;
import com.chuwa.iccblog.payload.security.SignUpDto;
import com.chuwa.iccblog.security.JwtTokenProvider;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/jwt")
public class AuthJWTController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping("/signin")
	public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDto.getAccountOrEmail(), loginDto.getPassword()
		));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		// get token from tokenProvider
		String token = tokenProvider.generateToken(authentication);


		return ResponseEntity.ok(new JWTAuthResponse(token));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

		// check if username is in a DB
		if (userRepository.existsByAccount(signUpDto.getAccount())) {
			return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
		}

		// check if email exists in DB
		if (userRepository.existsByEmail(signUpDto.getEmail())) {
			return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
		}

		// create user object
		User user = new User();
		user.setName(signUpDto.getName());
		user.setAccount(signUpDto.getAccount());
		user.setEmail(signUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

		Role roles = roleRepository.findByName("ROLE_ADMIN").get();
		user.setRoles(Collections.singleton(roles));
		userRepository.save(user);

		return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
	}
}
