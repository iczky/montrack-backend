package com.montrackBackend.montrack.users.service;

import com.montrackBackend.montrack.auth.dto.ForgotPasswordRequestDto;
import com.montrackBackend.montrack.auth.dto.ForgotPasswordResponseDto;
import com.montrackBackend.montrack.users.dto.RegisterRequestDto;
import com.montrackBackend.montrack.users.dto.RegisterResponseDto;
import com.montrackBackend.montrack.users.dto.SetPinReqDto;
import com.montrackBackend.montrack.users.dto.SetPinRespDto;
import com.montrackBackend.montrack.users.entity.User;

import java.util.List;

public interface UserService {
    RegisterResponseDto register(RegisterRequestDto registerRequestDto);
    User findByEmail(String email);
    List<User> getAllUser();
    User updateUser(User user);
    SetPinRespDto setPin(SetPinReqDto reqDto, String email);


}