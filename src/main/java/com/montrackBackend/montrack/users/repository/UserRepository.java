package com.montrackBackend.montrack.users.repository;

import com.montrackBackend.montrack.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
