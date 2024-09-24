package com.modsen.authenticationservice.core.mappers;

import com.modsen.authenticationservice.core.dto.UserLoginDTO;
import com.modsen.authenticationservice.core.dto.UserRegisterDTO;
import com.modsen.authenticationservice.entities.UserCredential;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserCredential registerDtoToCredential(UserRegisterDTO registerDto);

    UserCredential loginDtoToCredential(UserLoginDTO userLoginDTO);
}

