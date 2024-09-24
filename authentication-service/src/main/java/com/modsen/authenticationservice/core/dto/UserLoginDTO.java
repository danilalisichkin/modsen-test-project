package com.modsen.authenticationservice.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Entry form for login user")
public class UserLoginDTO {
    @NotNull
    @Size(min = 4, max = 100, message = "Username must be from 4 to 100 characters length.")
    private String username;

    @NotNull
    @Size(min = 4, max = 100, message = "Password must be from 4 to 100 characters length.")
    private String password;
}
