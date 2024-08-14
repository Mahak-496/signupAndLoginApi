
package com.example.signupAndLogin.service;
import com.example.signupAndLogin.configuration.JwtService;
import com.example.signupAndLogin.dto.mapper.UserMapper;
import com.example.signupAndLogin.dto.request.LoginRequestDto;
import com.example.signupAndLogin.dto.request.RegistrationRequestDto;
import com.example.signupAndLogin.dto.response.LoginResponseDto;
import com.example.signupAndLogin.dto.response.RegistrationResponseDto;
import com.example.signupAndLogin.entity.User;
import com.example.signupAndLogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtHelper;

    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtHelper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public RegistrationResponseDto registerUser(RegistrationRequestDto registrationRequestDto) {
        if (userRepository.findByEmail(registrationRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        User user = UserMapper.toUserEntity(registrationRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = userRepository.save(user);
        return UserMapper.toRegistrationResponseDTO(registeredUser);
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) throws AuthenticationException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
            );
            if (authentication.isAuthenticated()) {
                String token = jwtHelper.generateToken(loginRequestDto.getEmail());

                User user = userRepository.findByEmail(loginRequestDto.getEmail())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                user.setToken(token);
                userRepository.save(user);
                return UserMapper.toLoginResponseDTO(user, token);
            } else {
                throw new RuntimeException("Invalid email or password");
            }
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Invalid email or password");
        }
    }


    @Override
    public RegistrationResponseDto getUserByToken(String token) throws AuthenticationException {
        try {
            String username = jwtHelper.extractUsername(token);

//            if (username == null ) {
//                throw new AuthenticationException("Invalid or expired token") {};
//            }

            User user= userRepository.findByEmail(username)
                    .orElseThrow(() -> new AuthenticationException("User not found") {});

            return UserMapper.toRegistrationResponseDTO(user);


        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching user details", e);
        }
    }


}

