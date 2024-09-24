package com.modsen.authenticationservice.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterDTO {
    private String username;
    private String password;
}