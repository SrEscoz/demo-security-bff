package net.escoz.demosecuritybff.controllers;

import lombok.AllArgsConstructor;
import net.escoz.demosecuritybff.controllers.dtos.TokenOutDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

	@GetMapping
	public ResponseEntity<TokenOutDTO> admin() {
		return ResponseEntity
				.status(HttpStatus.I_AM_A_TEAPOT)
				.body(new TokenOutDTO(HttpStatus.I_AM_A_TEAPOT.value(), "Admin", "Never"));
	}
}