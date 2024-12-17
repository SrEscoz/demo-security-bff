package net.escoz.demosecuritybff.mappers;

import net.escoz.demosecuritybff.controllers.dtos.user.RegisterDTO;
import net.escoz.demosecuritybff.controllers.dtos.user.UserOutDTO;
import net.escoz.demosecuritybff.models.AppUser;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public abstract UserOutDTO toDTO(AppUser user);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(source = "username", target = "username")
	@Mapping(source = "createdAt", target = "createdAt")
	public abstract UserOutDTO toSimpleDTO(AppUser dto);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	public abstract AppUser toEntity(RegisterDTO registerDTO);

	@AfterMapping
	protected void updatePassword(@MappingTarget AppUser appUser) {
		appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
	}

	@AfterMapping
	protected void updateRoles(@MappingTarget AppUser appUser) {
		appUser.setRoles("USER");
	}
}
