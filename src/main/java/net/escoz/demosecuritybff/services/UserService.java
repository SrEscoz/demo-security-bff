package net.escoz.demosecuritybff.services;

import lombok.AllArgsConstructor;
import net.escoz.demosecuritybff.models.AppUser;
import net.escoz.demosecuritybff.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public AppUser addUser(AppUser user) {
		// Primero habría que comprobar duplicados
		return userRepository.save(user);
	}

	public List<AppUser> getAllUsers() {
		return userRepository.findAll();
	}

}
