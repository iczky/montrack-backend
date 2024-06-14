package com.montrackBackend.montrack.auth.service.Impl;

import com.montrackBackend.montrack.auth.entity.UserAuth;
import com.montrackBackend.montrack.exceptions.NotExistException;
import com.montrackBackend.montrack.users.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userData = userRepository.findByEmail(email).orElseThrow(() -> new NotExistException("Email is not found"));
        return new UserAuth(userData);
    }
}
