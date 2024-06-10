package com.montrackBackend.montrack.users.service;

import com.montrackBackend.montrack.users.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    List<User> getAllUser();

    User updateUser(User user);
}
