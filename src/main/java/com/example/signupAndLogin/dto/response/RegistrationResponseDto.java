package com.example.signupAndLogin.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponseDto {
    private String username;
    private String email;
    private String gender;
    private String phoneNumber;
    private String Address;
}
