package net.escoz.demosecuritybff.controllers.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

	@NotBlank(message = "Username must not be blank")
	private String username;

	@NotBlank(message = "Email must not be blank")
	private String email;

	@NotBlank(message = "Password must not be blank")
	private String password;
}
