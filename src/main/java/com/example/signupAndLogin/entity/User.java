package com.example.signupAndLogin.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "UserName is required")
    @Column(name = "user_name")
    private String username;

    @NotEmpty(message = "email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true, name = "user_email")
    private String email;

    @NotEmpty(message = "password is required")
    @Column( name = "user_password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
//    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}",
//            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;

    @Column( name = "user_gender")
    private String gender;

    @Column( name = "user_phone_number")
    private String phoneNumber;

    @Column( name = "user_address")
    private String Address;

}
