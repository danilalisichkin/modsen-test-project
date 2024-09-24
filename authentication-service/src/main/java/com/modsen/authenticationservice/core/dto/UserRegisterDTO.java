package com.modsen.authenticationservice.core.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterDTO {
    @NotNull
    @Size(min = 4, max = 100, message = "Username must be from 4 to 100 characters length.")
    private String username;

    @NotNull
    @Size(min = 4, max = 100, message = "Password must be from 4 to 100 characters length.")
    private String password;
}