package net.escoz.demosecuritybff.controllers.dtos;

import lombok.Builder;

@Builder
public record TokenOutDTO(int status, String token, String expiration) {
}
