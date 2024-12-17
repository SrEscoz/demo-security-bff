package net.escoz.demosecuritybff.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorOutDTO(String timestamp, int status, String error, List<String> errors) {
}
