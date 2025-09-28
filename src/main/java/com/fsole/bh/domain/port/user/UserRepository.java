package com.fsole.bh.domain.port.user;

import com.fsole.bh.domain.model.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();
    User getUserById(Long id);
}
