package com.example.signupAndLogin.dto.mapper;

import com.example.signupAndLogin.dto.request.LoginRequestDto;
import com.example.signupAndLogin.dto.request.RegistrationRequestDto;
import com.example.signupAndLogin.dto.response.LoginResponseDto;
import com.example.signupAndLogin.dto.response.RegistrationResponseDto;
import com.example.signupAndLogin.entity.User;

public class UserMapper {

    public static  User toUserEntity(RegistrationRequestDto registrationRequestDTO) {
        return User.builder()
                .username(registrationRequestDTO.getUsername())
                .email(registrationRequestDTO.getEmail())
                .password(registrationRequestDTO.getPassword())
                .gender(registrationRequestDTO.getGender())
                .phoneNumber(registrationRequestDTO.getPhoneNumber())
                .Address(registrationRequestDTO.getAddress())
                .build();
    }

    public static RegistrationResponseDto toRegistrationResponseDTO(User user) {
        return RegistrationResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .Address(user.getAddress())
                .build();
    }

    public static User toUserEntityForLogin(LoginRequestDto loginRequestDTO) {
        return User.builder()
                .username(loginRequestDTO.getEmail())
                .password(loginRequestDTO.getPassword())
                .build();
    }

    public static LoginResponseDto toLoginResponseDTO(User user, String token) {
        return LoginResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .token(token)
                .build();
    }
}
