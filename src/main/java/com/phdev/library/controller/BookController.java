package com.phdev.library.controller;

import com.phdev.library.dto.BookAvailableQuantityRequest;
import com.phdev.library.dto.BookRequest;
import com.phdev.library.dto.BookResponse;
import com.phdev.library.dto.BookUpdate;
import com.phdev.library.services.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> findAll(Pageable pageable) {
        Page<BookResponse> bookResponses = bookService.findAll(pageable);
        return ResponseEntity.ok().body(bookResponses);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookResponse> findById(@PathVariable Long id) {
        BookResponse bookResponse = bookService.findById(id);
        return ResponseEntity.ok().body(bookResponse);
    }

    @PostMapping
    public ResponseEntity<BookResponse> insert(@Valid @RequestBody BookRequest request) {
        BookResponse bookResponse = bookService.insert(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(bookResponse.id()).toUri();
        return ResponseEntity.created(uri).body(bookResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody BookUpdate update) {
        BookResponse bookResponse = bookService.update(id, update);
        return ResponseEntity.ok().body(bookResponse);
    }

    @PatchMapping(value = "/{id}/stock/add")
    public ResponseEntity<BookResponse> addCopiesToStock(@PathVariable Long id,
                                                         @Valid @RequestBody BookAvailableQuantityRequest request) {
        BookResponse bookResponse = bookService.addCopiesToStock(id, request);
        return ResponseEntity.ok().body(bookResponse);
    }

    @PatchMapping(value = "/{id}/stock/remove")
    public ResponseEntity<BookResponse> removeCopiesFromStock(@PathVariable Long id,
                                                              @Valid @RequestBody BookAvailableQuantityRequest request) {
        BookResponse bookResponse = bookService.removeCopiesFromStock(id, request);
        return ResponseEntity.ok().body(bookResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
