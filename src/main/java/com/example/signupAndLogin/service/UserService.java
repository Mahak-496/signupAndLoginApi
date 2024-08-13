////package com.example.signupAndLogin.service;
////
////import com.example.signupAndLogin.dto.mapper.UserMapper;
////import com.example.signupAndLogin.dto.request.LoginRequestDto;
////import com.example.signupAndLogin.dto.request.RegistrationRequestDto;
////import com.example.signupAndLogin.dto.response.LoginResponseDto;
////import com.example.signupAndLogin.dto.response.RegistrationResponseDto;
////import com.example.signupAndLogin.entity.User;
////import com.example.signupAndLogin.repository.UserRepository;
////import com.example.signupAndLogin.utils.PasswordUtils;
////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.security.authentication.BadCredentialsException;
////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
////import org.springframework.security.core.Authentication;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.stereotype.Service;
////
////import javax.naming.AuthenticationException;
////
////@Service
////public class UserService implements IUserService {
////    @Autowired
////    private UserRepository userRepository;
////
////    @Autowired
////    private BCryptPasswordEncoder passwordEncoder;
////
////    @Autowired
////    private AuthenticationManager authenticationManager;
////
////    @Override
////    public RegistrationResponseDto registerUser(RegistrationRequestDto registrationRequestDto) {
////        if (userRepository.findByEmail(registrationRequestDto.getEmail()) != null) {
////            throw new RuntimeException("User with this email already exists");
////        }
////        User user = UserMapper.toUserEntity(registrationRequestDto);
//////        user.setPassword(user.getPassword());
//////        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());
//////        user.setPassword(hashedPassword);
////        user.setPassword(passwordEncoder.encode(user.getPassword()));
////        User registeredUser = userRepository.save(user);
////        return UserMapper.toRegistrationResponseDTO(registeredUser);
////    }
////
//////    @Override
//////    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) throws AuthenticationException {
//////        User user = userRepository.findByEmail(loginRequestDto.getEmail());
//////
////////        try {
////////            Authentication authentication = authenticationManager
////////                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.
////////                            getEmail().
////////                            toLowerCase(),
////////                            loginRequestDto.getPassword()));
////////            User user = (User) authentication.getPrincipal();
////////            return UserMapper.toLoginResponseDTO(user);
////////        }catch (Exception e){
////////            throw new AuthenticationException("Invalid email or password");
////////        }
//////
//////
//////        if (user == null || !passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
//////            if (user == null || !PasswordUtils.checkPassword(loginRequestDto.getPassword(), user.getPassword())) {
//////                throw new AuthenticationException("Invalid email or password");
//////            }
//////}
//////            return UserMapper.toLoginResponseDTO(user);
//////        }
//////
////
////
////
////    @Override
////    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) throws AuthenticationException {
////        User user = userRepository.findByEmail(loginRequestDto.getEmail());
////        if (user == null) {
////            throw new AuthenticationException("User not found");
////        }
////        authenticateUser(loginRequestDto.getEmail(), loginRequestDto.getPassword());
////
////
////
////        return UserMapper.toLoginResponseDTO(user);
////    }
////
////    private void authenticateUser(String email, String password) throws AuthenticationException {
////        try {
////            authenticationManager.authenticate(
////                    new UsernamePasswordAuthenticationToken(email.toLowerCase(), password)
////            );
////        } catch (BadCredentialsException e) {
////            throw new AuthenticationException("Invalid email or password");
////        }
////    }
////}
//
//
//
//package com.example.signupAndLogin.service;
//
//import com.example.signupAndLogin.dto.mapper.UserMapper;
//import com.example.signupAndLogin.dto.request.LoginRequestDto;
//import com.example.signupAndLogin.dto.request.RegistrationRequestDto;
//import com.example.signupAndLogin.dto.response.LoginResponseDto;
//import com.example.signupAndLogin.dto.response.RegistrationResponseDto;
//import com.example.signupAndLogin.entity.User;
//import com.example.signupAndLogin.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import javax.naming.AuthenticationException;
//import java.util.Optional;
//
//@Service
//public class UserService implements IUserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Override
//    public RegistrationResponseDto registerUser(RegistrationRequestDto registrationRequestDto) {
//        if (userRepository.findByEmail(registrationRequestDto.getEmail()).isPresent()) {
//            throw new RuntimeException("User with this email already exists");
//        }
//
//        User user = UserMapper.toUserEntity(registrationRequestDto);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        User registeredUser = userRepository.save(user);
//        return UserMapper.toRegistrationResponseDTO(registeredUser);
//    }
//
//    @Override
//    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) throws AuthenticationException {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail().toLowerCase(), loginRequestDto.getPassword())
//            );
//
//            User user = userRepository.findByEmail(loginRequestDto.getEmail())
//                    .orElseThrow(() -> new AuthenticationException("User not found") {});
//
//            return UserMapper.toLoginResponseDTO(user);
//        } catch (BadCredentialsException e) {
//            throw new AuthenticationException("Invalid email or password") {};
//        }
//    }
//}
//


package com.example.signupAndLogin.service;

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

    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail().toLowerCase(),
                            loginRequestDto.getPassword()
                    )
            );

            User user = userRepository.findByEmail(loginRequestDto.getEmail())
                    .orElseThrow(() -> new AuthenticationException("User not found") {});

            return UserMapper.toLoginResponseDTO(user);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Invalid email or password") {};
        } catch (Exception e) {
            // Log the exception and rethrow if necessary
            throw new RuntimeException("Unexpected error occurred", e);
        }
    }

}

