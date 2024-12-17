package net.escoz.demosecuritybff.services;

import lombok.AllArgsConstructor;
import net.escoz.demosecuritybff.models.AppUser;
import net.escoz.demosecuritybff.repositories.UserRepository;
import net.escoz.demosecuritybff.security.DatabaseAuthManager;
import net.escoz.demosecuritybff.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final DatabaseAuthManager databaseAuthManager;
	private final AuthenticationManager authenticationManager;

	public AppUser addUser(AppUser user) {
		// Primero habría que comprobar duplicados
		return userRepository.save(user);
	}

	public String login(String email, String password) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(email, password)
			);

			if (authentication.isAuthenticated()) {
				return jwtService.generateToken(databaseAuthManager.loadByEmail(email));
			} else {
				throw new BadCredentialsException("Error: Invalid username or password");
			}

		} catch (AuthenticationException ex) {
			throw new BadCredentialsException("Error: Invalid username or password");
		}
	}

	public List<AppUser> getAllUsers() {
		return userRepository.findAll();
	}

}
