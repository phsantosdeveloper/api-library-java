package com.phdev.library.services;

import com.phdev.library.dto.UserResponse;
import com.phdev.library.dto.UserRequest;
import com.phdev.library.entities.User;
import com.phdev.library.repositories.UserRepository;
import com.phdev.library.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(user -> new UserResponse(user));
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        User user = findByUserId(id);
        return new UserResponse(user);
    }

    @Transactional
    public UserResponse insert(UserRequest request) {
        User user = new User(null, request.name(), request.email(), request.phone());
        userRepository.save(user);
        return new UserResponse(user);
    }

    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        User user = findByUserId(id);
        updateDataUser(user, request);
        userRepository.save(user);
        return new UserResponse(user);
    }

    @Transactional
    public void delete(Long id) {
        User user = findByUserId(id);
        userRepository.delete(user);
    }

    private void updateDataUser(User user, UserRequest request) {
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPhone(request.phone());
    }

    private User findByUserId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return user;
    }
}
