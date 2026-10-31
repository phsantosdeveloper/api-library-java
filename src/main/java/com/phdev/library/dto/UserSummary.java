package com.phdev.library.dto;

import com.phdev.library.entities.User;

public record UserSummary(
        Long id,
        String name
) {

    public UserSummary(User user) {
        this(user.getId(), user.getName());
    }
}
