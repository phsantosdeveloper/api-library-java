package com.phdev.library.controller;

import com.phdev.library.dto.AuthorRequest;
import com.phdev.library.dto.AuthorResponse;
import com.phdev.library.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<Page<AuthorResponse>> findAll(Pageable pageable) {
        Page<AuthorResponse> authorResponses = authorService.findAll(pageable);
        return ResponseEntity.ok().body(authorResponses);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AuthorResponse> findById(@PathVariable Long id) {
        AuthorResponse authorResponse = authorService.findById(id);
        return ResponseEntity.ok().body(authorResponse);
    }

    @GetMapping(params = "name")
    public ResponseEntity<Page<AuthorResponse>> findByName(@RequestParam(name = "name") String name, Pageable pageable) {
        Page<AuthorResponse> authorResponses = authorService.findByName(name, pageable);
        return ResponseEntity.ok().body(authorResponses);
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> insert(@Valid @RequestBody AuthorRequest request) {
        AuthorResponse authorResponse = authorService.insert(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(authorResponse.id()).toUri();

        return ResponseEntity.created(uri).body(authorResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AuthorResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody AuthorRequest request) {
        AuthorResponse authorResponse = authorService.update(id, request);
        return ResponseEntity.ok().body(authorResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
