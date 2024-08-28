package com.sunbase.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunbase.config.JwtProvider;
import com.sunbase.Dto.LoginResponse;
import com.sunbase.model.Admin;
import com.sunbase.repository.AdminRepository;
import com.sunbase.service.AdminUserDetailsService;
import com.sunbase.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AdminRepository adminRepo;

	private PasswordEncoder encoder;

	private JwtProvider jwtProvider;

	private AdminUserDetailsService adminUserDetailsService;

	private UserService customerService;
	
	@Value("${sunbase.login_id}")
	private String login_id;
	

	@Value("${sunbase.password}")
	private String password;
	

	@Autowired
	public AuthController(AdminRepository adminRepo, PasswordEncoder encoder, JwtProvider jwtProvider,
			AdminUserDetailsService adminUserDetailsService, UserService customerService) {
		super();
		this.adminRepo = adminRepo;
		this.encoder = encoder;
		this.jwtProvider = jwtProvider;
		this.adminUserDetailsService = adminUserDetailsService;
		this.customerService = customerService;
	}

	@PostMapping("/signup")
	public ResponseEntity<String> createUser(@RequestBody Admin admin) {

		Optional<Admin> findByEmail = adminRepo.findByEmail(admin.getEmail());
		if (findByEmail.isPresent())
			throw new RuntimeException("Email is Already Used");

		admin.setPassword(encoder.encode(admin.getPassword()));
		adminRepo.save(admin);

		return ResponseEntity.ok("Admin created Succesfully");
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> signin(@RequestBody Admin admin) {

		Authentication authentication = authenticate(admin.getEmail(), admin.getPassword());
		String jwt = jwtProvider.generateToken(authentication);

		String token = customerService.sunbaselogin(login_id,password);

		return ResponseEntity.ok(new LoginResponse(jwt, token));
	}

	private Authentication authenticate(String email, String password) {
		UserDetails userDetails = adminUserDetailsService.loadUserByUsername(email);
		if (userDetails == null) {
			throw new BadCredentialsException("invalid username and password");
		}

		if (!encoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("invalid password");
		}

		return new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());

	}

}
