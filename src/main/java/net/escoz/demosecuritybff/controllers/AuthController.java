package net.escoz.demosecuritybff.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.escoz.demosecuritybff.controllers.dtos.TokenOutDTO;
import net.escoz.demosecuritybff.controllers.dtos.user.LoginDTO;
import net.escoz.demosecuritybff.controllers.dtos.user.RegisterDTO;
import net.escoz.demosecuritybff.mappers.UserMapper;
import net.escoz.demosecuritybff.models.AppUser;
import net.escoz.demosecuritybff.security.JwtService;
import net.escoz.demosecuritybff.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	private final UserMapper userMapper;
	private final JwtService jwtService;
	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<TokenOutDTO> register(@Valid @RequestBody RegisterDTO request) {
		AppUser appUser = userService.addUser(userMapper.toEntity(request));

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new TokenOutDTO(HttpStatus.CREATED.value(), appUser.getEmail(), jwtService.getExpiration()));
	}

	@PostMapping("/login")
	public ResponseEntity<TokenOutDTO> login(@RequestBody LoginDTO request) {
		String token = userService.login(request.getEmail(), request.getPassword());

		return ResponseEntity
				.ok()
				.body(new TokenOutDTO(HttpStatus.OK.value(), token, jwtService.getExpiration()));
	}
}
