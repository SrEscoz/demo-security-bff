package net.escoz.demosecuritybff.controllers;

import lombok.AllArgsConstructor;
import net.escoz.demosecuritybff.controllers.dtos.user.UserOutDTO;
import net.escoz.demosecuritybff.mappers.UserMapper;
import net.escoz.demosecuritybff.models.AppUser;
import net.escoz.demosecuritybff.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
@AllArgsConstructor
public class PublicController {

	private final UserService userService;
	private final UserMapper userMapper;

	@GetMapping("/users")
	public ResponseEntity<List<UserOutDTO>> getAllUsers() {
		List<AppUser> users = userService.getAllUsers();
		List<UserOutDTO> usersDTO = users.stream().map(userMapper::toSimpleDTO).toList();

		return ResponseEntity
				.ok()
				.body(usersDTO);
	}
}
