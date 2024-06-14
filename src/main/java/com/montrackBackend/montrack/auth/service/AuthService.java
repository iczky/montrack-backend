package com.montrackBackend.montrack.auth.service;

import com.montrackBackend.montrack.auth.dto.ForgotPasswordRequestDto;
import com.montrackBackend.montrack.auth.dto.ForgotPasswordResponseDto;
import com.montrackBackend.montrack.auth.dto.LoginRequestDto;
import com.montrackBackend.montrack.auth.dto.LoginResponseDto;
import org.springframework.security.core.Authentication;

public interface AuthService {
    String generateToken(Authentication authentication);
    ForgotPasswordResponseDto forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto);
    String getOrGenerateToken(String email, Authentication authentication);
}
