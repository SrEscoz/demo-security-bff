package net.escoz.demosecuritybff.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@AllArgsConstructor(onConstructor_ = {@Lazy})
public class JwtAuthFilter extends OncePerRequestFilter {

	@Lazy
	private final JwtService jwtService;

	@Lazy
	private final DatabaseAuthManager appUserDetailService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
	                                @NonNull HttpServletResponse response,
	                                @NonNull FilterChain filterChain) throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if (request.getRequestURI().contains("/auth")) {
			filterChain.doFilter(request, response);
			return;
		}

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			response.setStatus(HttpServletResponse.SC_OK);
			filterChain.doFilter(request, response);
			return;
		}

		try {

			String token = authHeader.substring(7);
			String username = jwtService.extractUsername(token);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = appUserDetailService.loadUserByJwtUsername(username);

				if (userDetails != null && jwtService.isValidToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							username, userDetails.getPassword(), userDetails.getAuthorities()
					);
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);

					filterChain.doFilter(request, response);

				} else {
					throw new JwtException("Invalid token");
				}
			}
		} catch (JwtException ex) {
			throw new JwtException("Invalid token");
		}
	}
}
