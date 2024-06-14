package com.montrackBackend.montrack.users.service.Impl;

import com.montrackBackend.montrack.exceptions.NotExistException;
import com.montrackBackend.montrack.auth.dto.ForgotPasswordRequestDto;
import com.montrackBackend.montrack.auth.dto.ForgotPasswordResponseDto;
import com.montrackBackend.montrack.users.dto.RegisterRequestDto;
import com.montrackBackend.montrack.users.dto.RegisterResponseDto;
import com.montrackBackend.montrack.users.dto.SetPinReqDto;
import com.montrackBackend.montrack.users.dto.SetPinRespDto;
import com.montrackBackend.montrack.users.entity.User;
import com.montrackBackend.montrack.users.entity.UserPin;
import com.montrackBackend.montrack.users.repository.UserRepository;
import com.montrackBackend.montrack.users.service.UserService;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Log
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponseDto register(RegisterRequestDto user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new NotExistException("Email is already registered");
        }
        if (!Objects.equals(user.getPassword(), user.getPasswordMatch())){
            throw new RuntimeException("Password is wrong");
        }
        User newUser = user.toEntity();
        var password = passwordEncoder.encode(user.getPassword());
        newUser.setHashed_password(password);
        User savedUser = userRepository.save(newUser);
        return RegisterResponseDto.fromEntity(savedUser);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotExistException("Email does not exist"));
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        if(!userRepository.existsById(user.getId())){
            throw new RuntimeException("User is already exist");
        }
        return userRepository.save(user);
    }

    @Override
    public SetPinRespDto setPin(SetPinReqDto reqDto, String email) {
        log.info(reqDto.toString());
        log.info(email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotExistException("Email does not exist"));

        var newPin = new UserPin();
        newPin.setPin(passwordEncoder.encode(reqDto.getNewPin()));
        user.setPin(newPin);
        var savedUser = userRepository.save(user);
        return SetPinRespDto.fromEntity(savedUser);
    }

}
