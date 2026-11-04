package com.phdev.library.controller;

import com.phdev.library.dto.UserRequest;
import com.phdev.library.dto.UserResponse;
import com.phdev.library.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> findAll(Pageable pageable) {
        Page<UserResponse> userResponses = userService.findAll(pageable);
        return ResponseEntity.ok().body(userResponses);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        UserResponse userResponse = userService.findById(id);
        return ResponseEntity.ok().body(userResponse);
    }

    @GetMapping(params = "name")
    public ResponseEntity<Page<UserResponse>> findByName( @RequestParam(name = "name") String name, Pageable pageable) {
        Page<UserResponse> userResponses = userService.findByName(name, pageable);
        return ResponseEntity.ok().body(userResponses);
    }

    @GetMapping(params = "email")
    public ResponseEntity<UserResponse> findByEmail(@RequestParam(name = "email") String email) {
        UserResponse userResponse = userService.findByEmail(email);
        return ResponseEntity.ok().body(userResponse);
    }

    @PostMapping
    public ResponseEntity<UserResponse> insert(@Valid @RequestBody UserRequest request) {
        UserResponse userResponse = userService.insert(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(userResponse.id()).toUri();
        return ResponseEntity.created(uri).body(userResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody UserRequest request) {
        UserResponse userResponse = userService.update(id, request);
        return ResponseEntity.ok().body(userResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
