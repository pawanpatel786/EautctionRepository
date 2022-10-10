package com.iiht.eauction.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.eauction.exception.AccessDeniedException;
import com.iiht.eauction.jwtconfig.JwtUtils;
import com.iiht.eauction.model.CustomResponse;
import com.iiht.eauction.model.ERole;
import com.iiht.eauction.model.JwtResponse;
import com.iiht.eauction.model.LoginRequest;
import com.iiht.eauction.model.Role;
import com.iiht.eauction.model.SignupRequest;
import com.iiht.eauction.model.User;
import com.iiht.eauction.repository.RoleRepository;
import com.iiht.eauction.service.UserDetailsImpl;
import com.iiht.eauction.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/eAuction")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	RoleRepository roleRepository;


	@Autowired
	PasswordEncoder encoder;


	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(),userDetails.getUsername(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		if (userService.existsByEmail(signUpRequest.getEmail())) {
			CustomResponse response = new CustomResponse(HttpStatus.OK.value(), "Error: Email is already in use!",
					"Error");

			return ResponseEntity.ok().body(response);
		}

		// Create new user's account
		User user = new User(signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getAddress(),
				signUpRequest.getCity(), signUpRequest.getState(), signUpRequest.getPin(), signUpRequest.getPhone());
		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "Buyer":
					Role adminRole = roleRepository.findByName(ERole.ROLE_BUYER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_SELLER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userService.save(user);
		CustomResponse response = new CustomResponse(HttpStatus.OK.value(), "User registered successfully!", "Success");

		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/403")
	public ResponseEntity<?> getBidDetails() {
		throw new AccessDeniedException();
	}


}
