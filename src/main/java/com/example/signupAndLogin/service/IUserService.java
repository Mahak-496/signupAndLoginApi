package com.example.signupAndLogin.service;

import com.example.signupAndLogin.dto.request.LoginRequestDto;
import com.example.signupAndLogin.dto.request.RegistrationRequestDto;
import com.example.signupAndLogin.dto.response.LoginResponseDto;
import com.example.signupAndLogin.dto.response.RegistrationResponseDto;

import javax.naming.AuthenticationException;

public interface IUserService {

    RegistrationResponseDto registerUser(RegistrationRequestDto registrationRequestDto);

    LoginResponseDto loginUser(LoginRequestDto loginRequestDto) throws AuthenticationException;
}
