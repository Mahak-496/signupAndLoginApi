package com.example.signupAndLogin.controller;
import com.example.signupAndLogin.dto.request.LoginRequestDto;
import com.example.signupAndLogin.dto.request.RegistrationRequestDto;
import com.example.signupAndLogin.dto.response.LoginResponseDto;
import com.example.signupAndLogin.dto.response.RegistrationResponseDto;
import com.example.signupAndLogin.service.UserService;
import com.example.signupAndLogin.utils.ApiResponse;
import com.example.signupAndLogin.utils.ResponseSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController


public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/auth/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated RegistrationRequestDto registrationRequestDto) {
        try {
            RegistrationResponseDto response = userService.registerUser(registrationRequestDto);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("User registered successfully")
                    .data(response)
                    .statusCode(HttpStatus.CREATED.value())
                    .build();
            return ResponseSender.send(apiResponse);
        } catch (Exception e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        try {

            LoginResponseDto response = userService.loginUser(loginRequestDto);


            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Login successful")
                    .data(response)
                    .statusCode(HttpStatus.OK.value())
                    .build();

            return ResponseEntity.ok(apiResponse);
        } catch (AuthenticationException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Invalid email or password")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        } catch (Exception e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("An error occurred")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("An unexpected error occurred: " + e.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return ResponseSender.send(apiResponse);
    }
}





