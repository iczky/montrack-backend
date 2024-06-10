package com.montrackBackend.montrack.users.service.Impl;

import com.montrackBackend.montrack.users.entity.User;
import com.montrackBackend.montrack.users.repository.UserRepository;
import com.montrackBackend.montrack.users.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
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
}
