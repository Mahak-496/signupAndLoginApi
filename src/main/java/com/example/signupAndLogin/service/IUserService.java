package com.example.signupAndLogin.service;

import com.example.signupAndLogin.dto.request.LoginRequestDto;
import com.example.signupAndLogin.dto.request.RegistrationRequestDto;
import com.example.signupAndLogin.dto.response.LoginResponseDto;
import com.example.signupAndLogin.dto.response.RegistrationResponseDto;
import com.example.signupAndLogin.entity.User;

import javax.naming.AuthenticationException;

public interface IUserService {

    RegistrationResponseDto registerUser(RegistrationRequestDto registrationRequestDto);

    LoginResponseDto loginUser(LoginRequestDto loginRequestDto) throws AuthenticationException;

    RegistrationResponseDto getUserByToken(String token) throws AuthenticationException;
}
