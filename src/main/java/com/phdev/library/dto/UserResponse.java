package com.phdev.library.dto;

import com.phdev.library.entities.User;

public record UserResponse(
        Long id,
        String name,
        String email,
        String phone
) {

    public UserResponse(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPhone());
    }
}
