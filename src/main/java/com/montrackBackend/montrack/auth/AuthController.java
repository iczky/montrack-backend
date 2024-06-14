package com.montrackBackend.montrack.auth;

import com.montrackBackend.montrack.auth.dto.ForgotPasswordRequestDto;
import com.montrackBackend.montrack.auth.dto.LoginRequestDto;
import com.montrackBackend.montrack.auth.dto.LoginResponseDto;
import com.montrackBackend.montrack.auth.entity.UserAuth;
import com.montrackBackend.montrack.auth.service.AuthService;
import com.montrackBackend.montrack.response.Response;
import jakarta.servlet.http.Cookie;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

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

        String token = authService.getOrGenerateToken(loginRequestDto.getEmail(), authentication);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setMessage("Login Success");
        loginResponseDto.setToken(token);

        Cookie cookie = new Cookie("sid", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.SET_COOKIE, cookie.toString());


        return ResponseEntity.status(HttpStatus.OK).headers(header).body(loginResponseDto);
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequestDto requestDto){
        return Response.successResponse("Password successfully updated", authService.forgotPassword(requestDto));
    }
}
