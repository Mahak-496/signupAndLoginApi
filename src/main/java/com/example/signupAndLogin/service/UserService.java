package com.example.signupAndLogin.service;

import com.example.signupAndLogin.dto.mapper.UserMapper;
import com.example.signupAndLogin.dto.request.LoginRequestDto;
import com.example.signupAndLogin.dto.request.RegistrationRequestDto;
import com.example.signupAndLogin.dto.response.LoginResponseDto;
import com.example.signupAndLogin.dto.response.RegistrationResponseDto;
import com.example.signupAndLogin.entity.User;
import com.example.signupAndLogin.repository.UserRepository;
import com.example.signupAndLogin.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public RegistrationResponseDto registerUser(RegistrationRequestDto registrationRequestDto) {
        if (userRepository.findByEmail(registrationRequestDto.getEmail()) != null) {
            throw new RuntimeException("User with this email already exists");
        }

        User user = UserMapper.toUserEntity(registrationRequestDto);
//        user.setPassword(user.getPassword());

//        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());
//        user.setPassword(hashedPassword);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User registeredUser = userRepository.save(user);
        return UserMapper.toRegistrationResponseDTO(registeredUser);
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) throws AuthenticationException {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if (user == null || !passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
//            if (user == null || !PasswordUtils.checkPassword(loginRequestDto.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid email or password");
        }

        return UserMapper.toLoginResponseDTO(user);
    }



}
