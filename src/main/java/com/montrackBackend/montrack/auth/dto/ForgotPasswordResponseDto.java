package com.montrackBackend.montrack.auth.dto;

import com.montrackBackend.montrack.users.entity.User;
import lombok.Data;

@Data
public class ForgotPasswordResponseDto {
    private String email;
    private String username;

    public static ForgotPasswordResponseDto fromEntity(User user){
        ForgotPasswordResponseDto dto = new ForgotPasswordResponseDto();
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        return dto;
    }
}
