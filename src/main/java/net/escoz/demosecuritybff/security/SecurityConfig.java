package net.escoz.demosecuritybff.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable) // Deshabilitamos el CSRF para evitar problemas
				.authorizeHttpRequests(registry ->
						registry.requestMatchers("/auth/**", "/public/**").permitAll() // Rutas permitidas
								.requestMatchers("/admin").hasRole("ADMIN") // Rutas del admin
								.anyRequest().authenticated())
				.formLogin(AbstractAuthenticationFilterConfigurer::permitAll) // En caso de error, retorna el login
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Filtro previo para JWT
				.build();
	}

	/**
	 * Bean que determina el formato de codificación de las contraseñas
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
