package com.montrackBackend.montrack.users.dto;

import com.montrackBackend.montrack.users.entity.User;
import lombok.Data;

@Data
public class RegisterResponseDto {
    private String username;
    private String email;

    public static RegisterResponseDto fromEntity (User user){
        RegisterResponseDto dto = new RegisterResponseDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}


