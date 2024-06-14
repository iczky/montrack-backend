package com.montrackBackend.montrack.auth.service.Impl;

import com.montrackBackend.montrack.auth.dto.ForgotPasswordRequestDto;
import com.montrackBackend.montrack.auth.dto.ForgotPasswordResponseDto;
import com.montrackBackend.montrack.auth.dto.LoginRequestDto;
import com.montrackBackend.montrack.auth.dto.LoginResponseDto;
import com.montrackBackend.montrack.auth.service.AuthService;
import com.montrackBackend.montrack.exceptions.NotExistException;
import com.montrackBackend.montrack.users.entity.User;
import com.montrackBackend.montrack.users.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Log
@Service
public class AuthServiceImpl implements AuthService {
    private final JwtEncoder jwtEncoder;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthServiceImpl(JwtEncoder jwtEncoder, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        String scope= authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims =JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public ForgotPasswordResponseDto forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto) {
        Optional<User> userOptional = userRepository.findByEmail(forgotPasswordRequestDto.getEmail());
        if (userOptional.isEmpty()){
            throw new NotExistException("Email does not exist");
        }

        if (!Objects.equals(forgotPasswordRequestDto.getPassword(), forgotPasswordRequestDto.getPasswordMatch())){
            throw new RuntimeException("Password must be match");
        }

        User newUser = userOptional.get();

        newUser.setHashed_password(passwordEncoder.encode(forgotPasswordRequestDto.getPassword()));

        userRepository.save(newUser);
        return ForgotPasswordResponseDto.fromEntity(newUser);
    }
}
