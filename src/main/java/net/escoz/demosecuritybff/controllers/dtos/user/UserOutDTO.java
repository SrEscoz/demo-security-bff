package net.escoz.demosecuritybff.controllers.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOutDTO {

	private Long id;
	private String username;
	private String email;
	private String password;
	private String roles;
	private Date createdAt;
}
