package com.montrackBackend.montrack.users.dto;

import com.montrackBackend.montrack.users.entity.User;
import lombok.Data;

@Data
public class SetPinRespDto {
    private String email;
    private String message;

    public static SetPinRespDto fromEntity(User user){
        SetPinRespDto respDto = new SetPinRespDto();
        respDto.setEmail(user.getEmail());
        respDto.setMessage("Pin successfully created");
        return respDto;
    }
}
