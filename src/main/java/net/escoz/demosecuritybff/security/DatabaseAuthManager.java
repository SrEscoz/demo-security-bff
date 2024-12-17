package net.escoz.demosecuritybff.security;

import lombok.AllArgsConstructor;
import net.escoz.demosecuritybff.models.AppUser;
import net.escoz.demosecuritybff.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class DatabaseAuthManager implements AuthenticationManager {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		// Verificamos que el usuario existe en la bb dd
		AppUser user = userRepository.findByEmail((String) authentication.getPrincipal())
				.orElseThrow(() -> new UsernameNotFoundException("Error: User not found"));

		// Verificamos que la contraseña es correcta
		if (!passwordEncoder.matches((String) authentication.getCredentials(), user.getPassword())) {
			throw new BadCredentialsException("Error: Wrong password");
		}

		// Obtenemos los roles del usuario
		List<SimpleGrantedAuthority> authorities = Arrays.stream(getAuthorities(user))
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
				.toList();

		// Retornamos un objeto de tipo UsernamePasswordAuthenticationToken
		return new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);
	}

	private String[] getAuthorities(AppUser user) {
		String roles = user.getRoles();

		if (roles == null)
			return new String[]{"USER"};

		return roles.split(",");
	}
}
