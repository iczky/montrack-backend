package com.montrackBackend.montrack.users.dto;

import com.montrackBackend.montrack.users.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "You need to put the password")
    private String password;

    @NotBlank(message = "You need to put the same password")
    private String passwordMatch;

    public User toEntity(){
        User user = new User();
        user.setEmail(email);
        user.setUsername(name);
        user.setHashed_password(password);
        return user;
    }
}
