package com.montrackBackend.montrack.auth;

import com.montrackBackend.montrack.auth.dto.ForgotPasswordRequestDto;
import com.montrackBackend.montrack.auth.dto.LoginRequestDto;
import com.montrackBackend.montrack.auth.dto.LoginResponseDto;
import com.montrackBackend.montrack.auth.entity.UserAuth;
import com.montrackBackend.montrack.auth.service.AuthService;
import com.montrackBackend.montrack.response.Response;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
@Log
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
        log.info("Handling request for: " + loginRequestDto.getPassword());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getEmail(),
                    loginRequestDto.getPassword()));
        log.info(authentication.toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = authService.generateToken(authentication);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setMessage("Login Success");
        loginResponseDto.setToken(token);
        return Response.successResponse("Login Success", loginResponseDto);
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequestDto requestDto){
        return Response.successResponse("Password successfully updated", authService.forgotPassword(requestDto));
    }
}
