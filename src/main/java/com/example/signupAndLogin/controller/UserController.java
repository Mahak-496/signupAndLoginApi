package com.example.signupAndLogin.controller;
import com.example.signupAndLogin.dto.request.LoginRequestDto;
import com.example.signupAndLogin.dto.request.RegistrationRequestDto;
import com.example.signupAndLogin.dto.response.LoginResponseDto;
import com.example.signupAndLogin.dto.response.RegistrationResponseDto;
import com.example.signupAndLogin.entity.User;
import com.example.signupAndLogin.service.UserService;
import com.example.signupAndLogin.utils.ApiResponse;
import com.example.signupAndLogin.utils.ResponseSender;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Optional;

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

//    @PostMapping("/api/auth/login")
//    public ResponseEntity<ApiResponse> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
//        try {
//
//            LoginResponseDto response = userService.loginUser(loginRequestDto);
//            ApiResponse apiResponse = ApiResponse.builder()
//                    .message("Login successful")
//                    .data(response)
//                    .statusCode(HttpStatus.OK.value())
//                   .build();
//
//            return ResponseEntity.ok(apiResponse);
//       } catch (AuthenticationException e) {
//            ApiResponse apiResponse = ApiResponse.builder()
//                    .message("Invalid email or password")
//                   .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                    .build();
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
//        } catch (Exception e) {
//            ApiResponse apiResponse = ApiResponse.builder()
//                    .message("An error occurred")
//                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                    .build();
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
//        }
//    }



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
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("An error occurred")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
    @GetMapping("/userDetails/token")
    public ResponseEntity<?> getUserByToken(@RequestHeader("Authorization") String authorizationHeader) {
        try {

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization header format");
            }
            String token = authorizationHeader.substring(7);

            RegistrationResponseDto user = userService.getUserByToken(token);

            return ResponseEntity.ok(user);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
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





