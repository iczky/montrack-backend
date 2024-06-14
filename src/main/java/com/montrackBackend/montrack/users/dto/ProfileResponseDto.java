package com.montrackBackend.montrack.users.dto;

import com.montrackBackend.montrack.users.entity.User;
import lombok.Data;

@Data
public class ProfileResponseDto {
    private String name;
    private String email;
    private String quotes;

    public static ProfileResponseDto fromEntity(User user){
        ProfileResponseDto dto = new ProfileResponseDto();
        dto.setEmail(user.getEmail());
        dto.setName(user.getUsername());
        dto.setQuotes(user.getQuotes());
        return dto;
    }
}
