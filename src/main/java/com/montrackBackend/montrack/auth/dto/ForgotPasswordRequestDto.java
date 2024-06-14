package com.montrackBackend.montrack.auth.dto;

import com.montrackBackend.montrack.users.entity.User;
import lombok.Data;

@Data
public class ForgotPasswordRequestDto {
    private String email;
    private String password;
    private String passwordMatch;
}

